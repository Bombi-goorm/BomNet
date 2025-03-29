from typing import Optional

from pydantic import BaseModel

from app.model.Notification import NotificationType


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


class ResponseData(BaseModel):
    content: Optional[str] = None


class ChatbotResponseDto(BaseModel):
    crop: Optional[str] = None
    price: Optional[float] = None
    answer: Optional[str] = None
    location: Optional[str] = None
    weatherInfo: Optional[WeatherInfo] = None
    intent: Optional[str] = None
    response_data: Optional[ResponseData] = None

