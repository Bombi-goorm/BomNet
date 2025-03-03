import json
from fastapi import APIRouter, Depends
from openai import OpenAI
from sqlalchemy import and_
from sqlalchemy.orm import Session
from app.config import OPENAI_API_KEY
from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.model.Member import Member
from app.model.Notification import Notification, NotificationType
from app.model.NotificationCondition import NotificationCondition
from app.model.Product import Product
from app.model.Category import Category

# 비동기 Redis 클라이언트 사용 (redis.asyncio)
import redis.asyncio as redis

alert_router = APIRouter()
client = OpenAI(api_key=OPENAI_API_KEY)
redis_client = redis.Redis(host='localhost', port=6379, db=0, decode_responses=True)


def get_category_hierarchy(category: Category, db: Session):
    """ 부모(상위) 카테고리부터 현재 카테고리까지의 계층을 조회하는 함수 """
    hierarchy = []
    current_category = category
    while current_category:
        hierarchy.append(current_category)
        current_category = (
            db.query(Category)
            .filter(Category.category_id == current_category.parent_id)
            .first()
            if current_category.parent_id else None
        )
    return list(reversed(hierarchy))


@alert_router.post("/set")
async def create_notification(data: ChatbotRequestDto, db: Session = Depends(get_db)):
    """ 자연어 분석 후 알림 등록 """
    try:
        # 1. GPT에 JSON 형식으로 결과를 출력하도록 요청
        llm_response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {
                    "role": "system",
                    "content": (
                        "Extract the main product and its full variety from the user's request and output your result strictly as a JSON object. "
                        "Note: The 'item' should be the general category (e.g., '배추' or '사과'), and the 'variety' should be the full descriptor as given by the user. "
                        "For example, if the input is '고랭지배추', then 'item' should be '배추' and 'variety' should be '고랭지배추'."
                    )
                },
                {
                    "role": "user",
                    "content": f"Extract item, variety, and price from: '{data.input}'"
                }
            ],
            functions=[
                {
                    "name": "extract_notification_data",
                    "description": "Extract item, variety, and price from user input",
                    "parameters": {
                        "type": "object",
                        "properties": {
                            "item": {"type": "string", "description": "Main product (e.g., 사과 or 배추)"},
                            "variety": {"type": "string", "description": "Full variety (e.g., 홍옥, 고랭지배추 등)"},
                            "target_price": {"type": "number", "description": "Target price for notification"}
                        },
                        "required": ["item", "variety"]
                    }
                }
            ],
            function_call={"name": "extract_notification_data"}
        )

        # 2. LLM 응답에서 JSON 결과 파싱
        function_call = llm_response.choices[0].message.function_call
        if not function_call:
            return CommonResponseDto(status="400", message="❌ LLM 데이터 추출 실패", data=None)

        print("LLM function_call arguments:", function_call.arguments)
        parsed_data = json.loads(function_call.arguments)
        item = parsed_data.get("item")
        variety = parsed_data.get("variety")
        target_price = parsed_data.get("target_price", None)

        if not item or not variety:
            return CommonResponseDto(status="400", message="❌ 필수 정보 누락", data=None)

    except Exception as e:
        return CommonResponseDto(status="500", message=f"LLM 처리 오류: {str(e)}", data=None)

    # 3. DB에서 하위 품종 검색: variety 기준으로 조회 (예: "고랭지배추")
    child_category = db.query(Category).filter(Category.name == variety).first()
    if not child_category:
        return CommonResponseDto(status="404", message="해당 하위 품종을 찾을 수 없습니다.", data=None)
    print("Retrieved child_category:", child_category)

    # 4. 하위 품종의 부모 계층(대분류, 중분류 등) 조회
    category_hierarchy = get_category_hierarchy(child_category, db)
    print("Category hierarchy:", category_hierarchy)
    # 예시: 대분류, 중분류, 소분류 정보를 담은 딕셔너리로 활용 가능
    category_levels = {1: None, 2: None, 3: None}
    category_names = {1: None, 2: None, 3: None}
    for index, cat in enumerate(category_hierarchy, start=1):
        if index <= 3:
            category_levels[index] = str(cat.category_id)
            category_names[index] = cat.name

    # 5. 하위 품종에 연결된 상품(Product) 조회
    product = db.query(Product).filter(Product.category_id == child_category.category_id).first()
    if not product:
        return CommonResponseDto(status="404", message="해당 품목에 대한 상품 정보를 찾을 수 없습니다.", data=None)
    print("Retrieved product:", product)

    # 6. 사용자(Member) 조회
    # 실제 DB 컬럼은 "member_id"이므로 __table__.c.member_id 사용
    member = db.query(Member).filter(Member.__table__.c.member_id == data.bigId).first()
    if not member:
        return CommonResponseDto(status="404", message="해당 멤버를 찾을 수 없습니다.", data=None)

    # 7. 기존 알림 조건(중복 방지) 확인
    condition_filter = (
        NotificationCondition.target_price.is_(None)
        if target_price is None
        else NotificationCondition.target_price == target_price
    )
    existing_condition = db.query(NotificationCondition).filter(
        and_(
            NotificationCondition.member_id == member.id,
            NotificationCondition.product_id == product.id,
            condition_filter
        )
    ).first()
    if existing_condition:
        return CommonResponseDto(status="400", message="❌ 동일한 조건의 알림이 이미 존재합니다.", data=None)

    # 8. 알림 데이터 저장 (일반 알림)
    notification_type = NotificationType.TARGET_PRICE if target_price is not None else NotificationType.WEATHER
    notification = Notification(
        member_id=member.id,
        product_id=product.id,
        notification_type=notification_type,
        title=f"{item} {variety} 알림",
        message=f"{item} {variety}의 가격 변동을 확인하세요.",
        is_read="N"
    )
    db.add(notification)

    # 9. 지정가 알림 저장 및 Redis 캐싱
    if target_price is not None:
        condition = NotificationCondition(
            member_id=member.id,
            product_id=product.id,
            target_price=target_price,
            active="Y"
        )
        db.add(condition)

        redis_key = f"alert:{product.id}"
        existing_users = await redis_client.hget(redis_key, str(target_price))
        if existing_users:
            user_list = existing_users.split(",")
            if str(member.id) not in user_list:
                user_list.append(str(member.id))
        else:
            user_list = [str(member.id)]
        await redis_client.hset(redis_key, str(target_price), ",".join(user_list))

    db.commit()
    db.refresh(notification)

    return CommonResponseDto(
        status="200",
        message="✅ 알림이 저장되었습니다.",
        data=None
    )
