
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from app.controller.base_controller import router
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import PriceResponseDto


DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


@router.post("/price", response_model=CommonResponseDto[PriceResponseDto])
async def get_price(data: ChatbotRequestDto):

    price_data = PriceResponseDto(mid_id=data.mid_id, small_id=data.small_id, price=data.price)
    return CommonResponseDto(success=True, message=f"✅ {data.crop}의 현재 가격 정보!", data=price_data)