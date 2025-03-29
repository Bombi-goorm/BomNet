from typing import Optional

from pyasn1_modules.rfc2560 import ResponseData
from pydantic import BaseModel

from app.model.Notification import NotificationType


class ChatbotResponseDto(BaseModel):
    product_id: int
    price: int
    availability: int


class PriceResponseDto(BaseModel):
    productId: int  # 상품 ID
    midName: str  # 품목명
    midId: str  # 중분류 ID


class OtherResponseDto(BaseModel):
    answer: str


class WeatherInfo(BaseModel):
    weather: str
    temperature: str
    humidity: str
    windSpeed: str
    dateTime: str


class NotificationResponseDto(BaseModel):
    id: int
    member_id: int
    notification_type: NotificationType
    title: Optional[str]
    message: Optional[str]
    is_read: str


class ChatbotResponseDto(BaseModel):
    crop: Optional[str] = None
    price: Optional[float] = None
    answer: Optional[str] = None
    location: Optional[str] = None
    weatherInfo: Optional[WeatherInfo] = None
    intent: Optional[str] = None
    response_data: Optional[ResponseData] = None
