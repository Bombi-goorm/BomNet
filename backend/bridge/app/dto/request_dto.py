import datetime
from pydantic import BaseModel


# 요청

class RecommendationRequest(BaseModel):
    user_id: str


class PriceHistoryRequest(BaseModel):
    product_name: str
    start_date: datetime.date
    end_date: datetime.date


class SuitabilityRequest(BaseModel):
    soil_type: str
    climate_conditions: str


class WeatherAlertRequest(BaseModel):
    region: str


class WeatherForecastRequest(BaseModel):
    region: str
    date: datetime.date
