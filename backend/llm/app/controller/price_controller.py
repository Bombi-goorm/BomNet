from fastapi import HTTPException, APIRouter
from fastapi.params import Depends
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import Session

from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import PriceResponseDto
from app.model.ProductCode import ProductCode
from database import get_db

price_router = APIRouter()


@price_router.post("/info", response_model=CommonResponseDto[PriceResponseDto])
async def get_price(data: ChatbotRequestDto, db: Session = Depends(get_db)):
    print(f"🔍 입력 데이터: {data}")

    try:
        product = db.query(ProductCode).filter(
            ProductCode.big_id == str(data.bigId),
            ProductCode.mid_id == str(data.midId),
            ProductCode.small_id == str(data.smallId)
        ).first()

        print(f"🔍 조회 결과: {product}")

        if not product:
            raise HTTPException(status_code=404, detail="❌ 해당하는 품목/품종 정보를 찾을 수 없습니다.")

        price_data = PriceResponseDto(
            productId=product.id,  # ✅ 상품 ID 추가
            bigId=product.big_id,
            bigName=product.big_name,
            midId=product.mid_id,
            midName=product.mid_name,
            smallId=product.small_id,
            smallName=product.small_name,
        )

        print(f"✅ 반환 데이터: {price_data}")

        return CommonResponseDto(status='200', message="가격정보 조회 성공", data=price_data)

    except SQLAlchemyError as e:
        print(f"❌ DB 오류: {e}")
        db.rollback()  # 롤백
        raise HTTPException(status_code=500, detail=f"DB 오류 발생: {str(e)}")

    finally:
        db.close()  # DB 연결 해제
