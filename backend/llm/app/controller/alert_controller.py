import json
import re

from fastapi import APIRouter, Depends, Request
from openai import OpenAI
from sqlalchemy.orm import Session

from app.config import settings
from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.model.Category import Category
from app.model.NotificationCondition import NotificationCondition

alert_router = APIRouter()
client = OpenAI(api_key=settings.OPENAI_API_KEY)

# 상수 멤버 ID (예시)
# MEMBER_ID = "551653fc-8efb-4bc3-8fae-4053231a3233"

def get_category_hierarchy(category: Category, db: Session):
    """부모(상위) 카테고리부터 현재 카테고리까지의 계층을 조회하는 함수"""
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
async def create_notification(request: Request, data: ChatbotRequestDto, db: Session = Depends(get_db)):
    # if not data.member_id:
    #     raise HTTPException(status_code=401, detail="멤버를 찾을 수 없습니다.")

    member_id = request.state.member.id
    # print(member_id)
    # get_current_member(member_id=data.member_id, db=db)

    """자연어 분석 후 NotificationCondition을 DB에 저장하고 저장된 데이터를 반환 (Redis 저장 제거)"""
    try:
        # 1. GPT에 JSON 형식으로 결과 출력 요청
        llm_response = client.chat.completions.create(
            model="gpt-4-turbo",
            messages=[
                {
                    "role": "system",
                    "content": (
                        "Extract the main product, its full variety, target price, and the region/auction market from the user's request "
                        "and output your result strictly as a JSON object. Note: The 'item' should be the general category (e.g., '배추' or '사과'), "
                        "the 'variety' should be the full descriptor as given by the user (e.g., '고랭지배추'). For the region field, if a specific "
                        "region name is mentioned, return the names of the agricultural/seafood auction markets in that region as a single string "
                        "formatted like 'Name|Name|Name'. However, if an auction market name is directly provided in the input, then use that auction "
                        "market name for the region. (e.g., '가락시장|농협강서공판장')"
                        "auction market name for the region. Additionally, determine the price direction: 'U' for upward (or when "
                        "price is higher than a threshold) and 'D' for downward (or when price is lower than a threshold)."
                    )
                },
                {
                    "role": "user",
                    "content": f"Extract item, variety, target_price and region from: '{data.input}'"
                }
            ],
            functions=[
                {
                    "name": "extract_notification_data",
                    "description": "Extract item, variety, target price, and region from user input",
                    "parameters": {
                        "type": "object",
                        "properties": {
                            "item": {"type": "string", "description": "Main product (e.g., 사과 or 배추)"},
                            "variety": {"type": "string", "description": "Full variety (e.g., 홍옥, 고랭지배추 등)"},
                            "target_price": {"type": "number", "description": "Target price for notification"},
                            "region": {
                                "type": "string",
                                "description": (
                                    "Region or auction market name. If a region is provided, return the corresponding agricultural/seafood "
                                    "auction market names as a single string formatted like 'Name|Name|Name'. If an auction market name is directly "
                                    "provided, use that value."
                                )
                            },
                            "price_direction": {
                                "type": "string",
                                "description": "Price direction: 'U' for upward (price above or on target) or 'D' for downward (price below target)"
                            },
                            "market": {"type": "string", "description": "Optional: Auction market name provided directly"}
                        },
                        "required": ["item", "variety", "region", "market"]
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
        region = parsed_data.get("region")
        market = parsed_data.get("market")
        price_direction = parsed_data.get("price_direction", "U")

        if not item or not variety or not region:
            return CommonResponseDto(status="400", message="❌ 필수 정보 누락", data=None)

        # 2-1. 지역 및 시장 처리:
        # - 만약 market 값(시장명이 직접 제공됨)이 있으면, region은 무시하고 시장명만 사용
        # - 그렇지 않으면, region 필드에 있는 지역명을 이용해 해당 지역의 모든 시장명을 조회
        auction_market_by_region = {
            "서울": ["서울가락", "서울강서"],
            "부산": ["부산엄궁", "부산반여", "부산국제수산"],
            "울산": ["울산"],
            "포항": ["포항"],
            "강원": ["원주", "춘천", "강릉"],
            "강원도": ["원주", "춘천", "강릉"],
            "경기": ["수원", "안양", "안산", "구리"],
            "경기도": ["수원", "안양", "안산", "구리"],
            "인천": ["인천남촌", "인천삼산"],
            "광주": ["광주각화", "광주서부"],
            "전남": ["순천"],
            "전라남도": ["순천"],
            "전북": ["정읍", "익산", "전주"],
            "전라북도": ["정읍", "익산", "전주"],
            "대전": ["대전오정", "대전노은"],
            "충북": ["청주", "충주"],
            "충청북도": ["청주", "충주"],
            "충남": ["천안"],
            "충청남도": ["천안"],
            "대구": ["대구북부"],
            "경북": ["안동", "구미"],
            "경상북도": ["안동", "구미"],
            "경남": ["진주", "창원팔용", "창원내서"],
            "경상남도": ["진주", "창원팔용", "창원내서"]
        }

        if market and market.strip():
            # 직접 시장명이 제공된 경우: region은 무시하고 시장명만 사용
            market_list = re.split(r'[,|]', market.strip())
            market_list = [m.strip() for m in market_list if m.strip()]
            final_market = "|".join(market_list)
            selected_markets = market_list
            selected_regions = []  # 시장명이 직접 제공되었으므로, region 정보는 사용하지 않음.
            db_region_value = ""  # DB에는 region 정보를 저장하지 않음.
        else:
            # market 값이 제공되지 않은 경우, region 필드에 있는 지역명을 기반으로 시장명 조회
            region_list = re.split(r'[,|]', region)
            # "서울과 부산" 등 접속사가 포함된 경우에도 분리
            region_list = [r.strip() for seg in region_list for r in re.split(r'(?:과|와|및)', seg) if r.strip()]
            selected_regions = region_list
            selected_markets = []
            for reg in region_list:
                if reg in auction_market_by_region:
                    selected_markets.extend(auction_market_by_region[reg])
            final_market = "|".join(selected_markets)
            db_region_value = final_market

        print("Selected regions:", selected_regions)
        print("Selected markets:", selected_markets)
        print("Final auction market string:", final_market)

    except Exception as e:
        return CommonResponseDto(status="500", message=f"LLM 처리 오류: {str(e)}", data=None)

    # 3. DB에서 하위 품종 검색 (예: "홍옥")
    child_category = db.query(Category).filter(Category.name == variety).first()
    if not child_category:
        return CommonResponseDto(status="404", message="지원불가한 상품입니다.", data=None)
    print("Retrieved child_category:", child_category)

    # 4. 하위 품종의 부모 계층(대분류, 중분류 등) 조회
    category_hierarchy = get_category_hierarchy(child_category, db)
    print("Category hierarchy:", category_hierarchy)
    # 최대 3단계 카테고리 이름 저장 (예: {1: '과실류', 2: '사과', 3: '홍옥'})
    category_names = {1: None, 2: None, 3: None}
    for index, cat in enumerate(category_hierarchy, start=1):
        if index <= 3:
            category_names[index] = cat.name

    # 5. 사용자당 활성화된 알림 조건 등록 개수 제한 확인 (동일 상품과 상관없이 최대 5개)
    user_notification_count = db.query(NotificationCondition).filter(
        NotificationCondition.member_id == member_id,
        NotificationCondition.active == "T"
    ).count()
    if user_notification_count >= 5:
        return CommonResponseDto(
            status="400",
            message="❌ 사용자당 알림은 최대 5개까지 등록할 수 있습니다.",
            data=None
        )

    # 5-1. 가격 조건 처리: 입력 문구에 따라 가격 조건을 문자열로 변환하여 바로 사용 가능한 형식으로 저장
    # "3000원 되면 알려줘" -> "U"
    # "3000원 이상일때 알려줘" -> "U"
    # "3000원 이하일때 알려줘" -> "D"
    # if "이상" in data.input:
    #     price_direction = "U"
    # elif "이하" in data.input:
    #     price_direction = "D"
    # else:
    #     price_direction = "U"

    # 6. target_price가 존재할 경우 NotificationCondition 저장
    db_condition = None
    if target_price is not None:
        db_condition = NotificationCondition(
            member_id=member_id,
            target_price=target_price,  # 가격 조건을 바로 사용할 수 있는 형식으로 저장
            price_direction=price_direction,
            active="T",
            category=category_names.get(1, ""),
            item=category_names.get(2, ""),
            variety=category_names.get(3, ""),
            # 직접 시장명이 제공된 경우에는 region 필드를 빈 문자열로, 아니면 조회된 최종 시장명 사용
            region=db_region_value
        )
        db.add(db_condition)

    db.commit()
    db_data = {}
    if db_condition:
        db.refresh(db_condition)
        db_data = {
            "id": db_condition.id,
            "target_price": db_condition.target_price,
            "active": db_condition.active,
            "category": db_condition.category,
            "item": db_condition.item,
            "variety": db_condition.variety,
            "region": db_condition.region,
            "member_id": db_condition.member_id,
        }

    return {
        "status": "200",
        "message": "✅ 알림 조건이 저장되었습니다.",
        "data": {
            "notification_condition": db_data,
            "selected_regions": selected_regions,
            "selected_markets": selected_markets
        }
    }