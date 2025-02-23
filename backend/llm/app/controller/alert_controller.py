from types import NoneType

from fastapi import APIRouter
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto


alert_router = APIRouter()


DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


@alert_router.post("/", response_model=CommonResponseDto[NoneType])
async def set_alert(data: ChatbotRequestDto):
    """사용자가 원하는 가격 알람을 설정"""
    return CommonResponseDto(success=True, message=f"🔔 {data.item} 알람이 {data.price}원으로 설정되었습니다.", data=None)
