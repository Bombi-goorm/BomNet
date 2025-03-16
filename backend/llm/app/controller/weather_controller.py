from fastapi import APIRouter, HTTPException, Depends
from google.cloud import bigquery
from sqlalchemy.orm import Session

from app.config import settings
from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import WeatherResponseDto, WeatherInfo
from app.member_auth_handler import get_current_member

weather_router = APIRouter()

client = bigquery.Client(project=settings.GCP_PROJECT_ID)

GCP_PROJECT_ID = settings.GCP_PROJECT_ID
DATASET_ID = settings.DATASET_ID
TABLE_ID = settings.TABLE_ID


@weather_router.post("/info", response_model=CommonResponseDto[WeatherResponseDto])
async def get_weather(data: ChatbotRequestDto, db: Session = Depends(get_db)):

    if not data.member_id:
        raise HTTPException(status_code=401, detail="멤버를 찾을 수 없습니다.")
    get_current_member(member_id=data.member_id, db=db)


    """
    사용자 입력 `region` 값을 받아 BigQuery에서 날씨 데이터 조회.
    서버 연동에 실패하면 샘플 데이터를 반환합니다.
    """
    try:
        query = "SELECT"
        + " *"
        + " FROM `goorm-bomnet.kma.int_kma_pivoted_short`"
        + " WHERE fcst_date_time >= @startFcstTime and fcst_date_time <= @endFcstTime"
        + " AND nx = @nx AND ny = @ny"
        + " ORDER BY fcst_date_time"
        + " LIMIT 10";

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
