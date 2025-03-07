import random

from fastapi import HTTPException, APIRouter, Depends
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import Session

from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import PriceResponseDto
from app.model.Category import Category
from app.model.Product import Product

price_router = APIRouter()


def get_category_hierarchy(category: Category, db: Session):
    """
    ✅ 최상위 부모까지 모든 카테고리 정보를 조회하는 함수
    """
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

    return list(reversed(hierarchy))  # 최상위 부모부터 순서대로 정렬


@price_router.post("/info", response_model=CommonResponseDto[PriceResponseDto])
async def get_price(data: ChatbotRequestDto, db: Session = Depends(get_db)):
    """
    ✅ 중분류(품목) 입력을 기반으로 상품 ID와 가격 정보 조회
    """
    print(f"🔍 입력 데이터: {data}")

    try:
        # 1. 입력된 품목(중분류) 이름으로 카테고리 조회
        mid_category = db.query(Category).filter(Category.name == data.input).first()
        if not mid_category:
            raise HTTPException(
                status_code=404,
                detail=f"❌ '{data.input}'에 해당하는 품목(중분류)을 찾을 수 없습니다."
            )

        # 2. 카테고리 계층 구조 조회 (최상위 부모까지)
        category_hierarchy = get_category_hierarchy(mid_category, db)

        # 3. 하위 품종(소분류) 조회 (mid_category의 기본키는 category_id)
        small_categories = db.query(Category).filter(Category.parent_id == mid_category.category_id).all()
        if not small_categories:
            raise HTTPException(
                status_code=404,
                detail=f"❌ '{data.input}'에 해당하는 하위 품종(소분류)이 존재하지 않습니다."
            )

        # 4. 하위 품종 중 랜덤으로 하나 선택
        selected_small_category = random.choice(small_categories)

        # 5. 최종 계층 정보 구성 (대분류, 중분류, 소분류)
        # 초기값: 소분류는 선택된 카테고리의 category_id와 name, 대분류 및 중분류는 나중에 설정
        category_levels = {1: None, 2: None, 3: str(selected_small_category.category_id)}
        category_names = {1: None, 2: mid_category.name, 3: selected_small_category.name}

        for index, cat in enumerate(category_hierarchy, start=1):
            if index <= 2:  # 대분류, 중분류까지만 설정
                category_levels[index] = str(cat.category_id)
                category_names[index] = cat.name

        # 6. 선택된 소분류(품종)의 상품 조회
        product = db.query(Product).filter(Product.category_id == selected_small_category.category_id).first()
        if not product:
            raise HTTPException(
                status_code=404,
                detail=f"❌ '{selected_small_category.name}'에 해당하는 상품을 찾을 수 없습니다."
            )

        # 7. 최종 응답 데이터 구성
        price_data = PriceResponseDto(
            productId=product.id,
            bigId=category_levels[1],
            bigName=category_names[1],
            midId=category_levels[2],
            midName=category_names[2],
            smallId=category_levels[3],
            smallName=category_names[3],
        )

        print(f"✅ 반환 데이터: {price_data}")
        return CommonResponseDto(status='200', message="✅ 가격 정보 조회 성공", data=price_data)

    except SQLAlchemyError as e:
        print(f"❌ DB 오류: {e}")
        db.rollback()  # 오류 발생 시 롤백
        raise HTTPException(status_code=500, detail=f"DB 오류 발생: {str(e)}")
    finally:
        db.close()  # DB 연결 해제
