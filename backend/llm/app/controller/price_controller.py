from fastapi import HTTPException, APIRouter, Depends
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import Session

import logging

from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.model.Category import Category


logger = logging.getLogger("price_logger")
price_router = APIRouter()


@price_router.post("/info", response_model=CommonResponseDto[str])
async def get_price(data: ChatbotRequestDto, db: Session = Depends(get_db)):
    """
    ✅ 품목 입력이 존재하는지 확인
    """
    try:
        mid_category = db.query(Category).filter(Category.name == data.input).first()

        if not mid_category:
            logger.warning(f"[품목 조회 실패] 해당 품목 없음 - input={data.input}")
            raise CommonResponseDto(
                status='404',
                message=f"❌ '{data.input}'에 해당하는 품목(중분류)을 찾을 수 없습니다.",
                data=None
            )

        return CommonResponseDto(status='200', message="✅ 상품 조회 성공", data=mid_category.name)

    except SQLAlchemyError as e:
        logger.exception(f"[DB 오류] 품목 조회 실패 - input={data.input}")
        raise HTTPException(status_code=500, detail=f"DB 오류 발생: {str(e)}")
    finally:
        db.close()  # DB 연결 해제
