from typing import Optional

from pydantic import BaseModel

from app.model.Notification import NotificationType


class ChatbotResponseDto(BaseModel):
    product_id: int
    price: int
    availability: int


class PriceResponseDto(BaseModel):
    productId: int  # 상품 ID
    bigId: str  # 대분류 ID
    bigName: str  # 대분류명
    midName: str  # 품목명
    midId: str  # 중분류 ID
    smallName: str  # 품종명
    smallId: str  # 소분류 ID


class OtherResponseDto(BaseModel):
    answer: str


class WeatherInfo(BaseModel):
    weather: str
    temperature: str
    humidity: str
    wind: str
    dateTime: str


class WeatherResponseDto(BaseModel):
    location: str
    weatherInfo: WeatherInfo


class NotificationResponseDto(BaseModel):
    id: int
    member_id: int
    notification_type: NotificationType
    title: Optional[str]
    message: Optional[str]
    is_read: str
