from typing import List

from fastapi import APIRouter
import json

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import *
from app.dto.response_dto import *

router = APIRouter()

# Database connection setup
DATABASE_URL = "mysql+pymysql://root:1234@mariadb:3306/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


@router.get("/health")
def health():
    return {"Bridge :: Healthy"}


# API 정의
@router.post("/best", response_model=CommonResponseDto[List[Best5Response]])
def get_best5():
    return CommonResponseDto[List[Best5Response]](
        status="success",
        message="Top 5 products",
        data=[
            {"product_name": "사과", "price": 2000, "availability": 50},
            {"product_name": "배추", "price": 1500, "availability": 30},
        ]
    )


@router.post("/recommend", response_model=CommonResponseDto[List[RecommendationResponse]])
def get_recommendations(request: RecommendationRequest):
    return CommonResponseDto[List[RecommendationResponse]](
        status="success",
        message="Recommended products",
        data=[
            {"product_name": "감자", "reason": "현재 파종 적기"},
            {"product_name": "상추", "reason": "농지 적합"},
        ]
    )


@router.post("/price", response_model=CommonResponseDto[List[PriceHistoryResponse]])
def get_price_history(request: PriceHistoryRequest):
    return CommonResponseDto[List[PriceHistoryResponse]](
        status="success",
        message="Price history",
        data=[
            {"date": request.start_date, "price": 1800},
            {"date": request.end_date, "price": 2200},
        ]
    )


@router.post("/suitability", response_model=CommonResponseDto[List[SuitabilityResponse]])
def check_suitability(request: SuitabilityRequest):
    return CommonResponseDto[List[SuitabilityResponse]](
        status="success",
        message="Suitability analysis",
        data=[
            {"product_name": "토마토", "suitability_score": 0.85},
            {"product_name": "고구마", "suitability_score": 0.78},
        ]
    )


@router.post("/weather/alert", response_model=CommonResponseDto[List[WeatherAlertResponse]])
def get_weather_alerts(request: WeatherAlertRequest):
    return CommonResponseDto[List[WeatherAlertResponse]](
        status="success",
        message="Weather alerts",
        data=[
            {"region": request.region, "alert_type": "태풍 경보", "description": "강한 태풍이 접근 중입니다."},
        ]
    )


@router.post("/weather/forecast", response_model=CommonResponseDto[List[WeatherForecastResponse]])
def get_weather_forecast(request: WeatherForecastRequest):
    return CommonResponseDto[List[WeatherForecastResponse]](
        status="success",
        message="Weather forecast",
        data=[
            {"region": request.region, "date": request.date, "temperature": 25.3, "precipitation": 0.8,
             "condition": "맑음"},
        ]
    )
