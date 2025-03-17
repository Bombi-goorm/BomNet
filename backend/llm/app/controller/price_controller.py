import random

from fastapi import HTTPException, APIRouter, Depends
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import Session

from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import PriceResponseDto
from app.member_auth_handler import get_current_member
from app.model.Category import Category
from app.model.Product import Product

price_router = APIRouter()


@price_router.post("/info", response_model=CommonResponseDto[str])
async def get_price(data: ChatbotRequestDto, db: Session = Depends(get_db)):

    # if not data.member_id:
    #     raise HTTPException(status_code=401, detail="멤버를 찾을 수 없습니다.")
    # get_current_member(member_id=data.member_id, db=db)


    """
    ✅ 중분류(품목) 입력이 존재하는지 확인
    """
    print(f"🔍 입력 데이터: {data}")

    try:
        mid_category = db.query(Category).filter(Category.name == data.input).first()

        if not mid_category:
            raise CommonResponseDto(
                status='404',
                message=f"❌ '{data.input}'에 해당하는 품목(중분류)을 찾을 수 없습니다.",
                data=None
            )

        return CommonResponseDto(status='200', message="✅ 상품 조회 성공", data=mid_category.name)

    except SQLAlchemyError as e:
        print(f"❌ DB 오류: {e}")
        raise HTTPException(status_code=500, detail=f"DB 오류 발생: {str(e)}")
    finally:
        db.close()  # DB 연결 해제
