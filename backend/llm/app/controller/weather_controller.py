from fastapi import APIRouter, HTTPException
from google.cloud import bigquery

from app.config import settings
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import WeatherResponseDto, WeatherInfo

weather_router = APIRouter()

# BigQuery 클라이언트 설정: 프로젝트 ID를 settings를 통해 전달
client = bigquery.Client(project=settings.GCP_PROJECT_ID)

# GCP 프로젝트 및 BigQuery 데이터셋, 테이블 정보는 settings에서 읽어옴
GCP_PROJECT_ID = settings.GCP_PROJECT_ID
DATASET_ID = settings.DATASET_ID
TABLE_ID = settings.TABLE_ID


@weather_router.post("/info", response_model=CommonResponseDto[WeatherResponseDto])
async def get_weather(data: ChatbotRequestDto):
    """
    사용자 입력 `region` 값을 받아 BigQuery에서 날씨 데이터 조회.
    서버 연동에 실패하면 샘플 데이터를 반환합니다.
    """
    try:
        query = f"""
            SELECT location, weather, temperature, humidity, wind, datetime
            FROM `{GCP_PROJECT_ID}.{DATASET_ID}.{TABLE_ID}`
            WHERE location = @region
            ORDER BY datetime DESC
            LIMIT 1
        """

        job_config = bigquery.QueryJobConfig(
            query_parameters=[
                bigquery.ScalarQueryParameter("region", "STRING", data.region)
            ]
        )

        query_job = client.query(query, job_config=job_config)
        result = query_job.result()

        row = next(result, None)
        if row:
            weather_info = WeatherResponseDto(
                location=row.location,
                weatherInfo=WeatherInfo(
                    weather=row.weather,
                    temperature=row.temperature,
                    humidity=row.humidity,
                    wind=row.wind,
                    dateTime=row.datetime.isoformat() + "Z"
                )
            )
            return CommonResponseDto(
                status="200",
                message="날씨 정보 조회 성공",
                data=weather_info
            )
        else:
            return CommonResponseDto(
                status="404",
                message=f"{data.region}에 대한 날씨 데이터를 찾을 수 없습니다.",
                data=None
            )

    except Exception as e:
        # 서버 연동 실패 시 원래 제공된 샘플 데이터로 응답
        sample_weather_info = WeatherResponseDto(
            location="서울",
            weatherInfo=WeatherInfo(
                weather="맑음",
                temperature="5°C",
                humidity="30%",
                wind="10km/h",
                dateTime="2023-02-01T12:00:00Z"
            )
        )
        return CommonResponseDto(
            status="200",
            message="서버 연결 실패. 샘플 날씨 정보 반환.",
            data=sample_weather_info
        )
