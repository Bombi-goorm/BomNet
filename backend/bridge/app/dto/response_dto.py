import datetime

from pydantic import BaseModel


# 응답
class Best5Response(BaseModel):
    product_name: str
    price: float
    availability: int


class RecommendationResponse(BaseModel):
    product_name: str
    reason: str


class PriceHistoryResponse(BaseModel):
    date: datetime.date
    price: float


class SuitabilityResponse(BaseModel):
    product_name: str
    suitability_score: float


class WeatherAlertResponse(BaseModel):
    region: str
    alert_type: str
    description: str


class WeatherForecastResponse(BaseModel):
    region: str
    date: datetime.date
    temperature: float
    precipitation: float
    condition: str
