from datetime import datetime, timedelta

from fastapi import APIRouter, Depends
from google.cloud import bigquery
import json
import logging
from google.oauth2 import service_account
from sqlalchemy import or_, func
from sqlalchemy.orm import Session

from app.config import settings
from app.database import get_db
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import ChatbotResponseDto, WeatherInfo
from app.model.Region import Region

weather_router = APIRouter()
logger = logging.getLogger("weather_logger")

# ✅ JSON 문자열에서 credentials 객체 생성
credential_dict = json.loads(settings.GOOGLE_APPLICATION_CREDENTIALS)
credentials = service_account.Credentials.from_service_account_info(credential_dict)

# BigQuery 클라이언트 초기화
# client = bigquery.Client(project=settings.GCP_PROJECT_ID)
client = bigquery.Client(credentials=credentials, project=settings.GCP_PROJECT_ID)

# GCP 프로젝트 및 데이터셋 설정
GCP_PROJECT_ID = settings.GCP_PROJECT_ID
DATASET_ID = settings.DATASET_ID
TABLE_ID = settings.TABLE_ID


@weather_router.post("/info", response_model=CommonResponseDto[ChatbotResponseDto])
async def get_weather(data: ChatbotRequestDto, db: Session = Depends(get_db)):
    try:
        # ✅ 1. DB에서 지역 정보 조회
        region = get_region_by_keyword(db, data.input)

        # ✅ 2. BigQuery 날씨 정보 조회
        weather_response = get_weather_forecast(region, client)

        return CommonResponseDto(
            status="200",
            message="날씨 정보 조회 성공",
            data=weather_response
        )

    except ValueError as ve:
        logger.warning(f"[날씨 조회 실패] 지역 검색 실패 - {ve}")
        return CommonResponseDto(
            status="404",
            message="[날씨 조회 실패] 지역 검색 실패",
            data=None
        )

    except Exception as e:
        logger.error(f"[ERROR] BigQuery 또는 DB 오류 - region={data.region}", exc_info=True)
        sample_weather_info = ChatbotResponseDto(
            location="서울",
            weatherInfo=WeatherInfo(
                weather="맑음",
                temperature="5°C",
                humidity="30%",
                windSpeed="10km/h",
                dateTime="2023-02-01T12:00:00Z"
            )
        )
        return CommonResponseDto(
            status="200",
            message="서버 연결 실패. 샘플 날씨 정보 반환.",
            data=sample_weather_info
        )


def get_weather_forecast(region: Region, bigquery_client: bigquery.Client) -> ChatbotResponseDto:
    start_time = datetime.now().replace(hour=0, minute=0, second=0, microsecond=0)
    end_time = start_time + timedelta(hours=12)


    query = """
            SELECT fcst_date_time, TMP, REH, WSD, SKY, PTY
            FROM `goorm-bomnet.kma.int_kma_pivoted_short`
            WHERE fcst_date_time >= @start_time AND fcst_date_time <= @end_time
            AND nx = @nx AND ny = @ny
            ORDER BY fcst_date_time ASC
            LIMIT 1
        """

    job_config = bigquery.QueryJobConfig(
        query_parameters=[
            bigquery.ScalarQueryParameter("start_time", "STRING", start_time.strftime("%Y-%m-%d %H:%M:%S")),
            bigquery.ScalarQueryParameter("end_time", "STRING", end_time.strftime("%Y-%m-%d %H:%M:%S")),
            bigquery.ScalarQueryParameter("nx", "STRING", region.xx),
            bigquery.ScalarQueryParameter("ny", "STRING", region.yy),
        ]
    )

    query_job = bigquery_client.query(query, job_config=job_config)
    results = query_job.result()
    row = next(results, None)

    if row is None:
        raise ValueError("예보 결과가 존재하지 않습니다.")

    for row in results:
        weather_info = WeatherInfo(
            weather=row.get("SKY"),
            temperature=row.get("TMP"),
            humidity=row.get("REH"),
            windSpeed=row.get("WSD"),
            dateTime=row.get("fcst_date_time")
        )
        # ✅ 첫 번째 결과만 사용하고 바로 리턴
        return ChatbotResponseDto(
            location=region.si_gun_gu_name,
            weatherInfo=weather_info
        )


def get_region_by_keyword(session: Session, keyword: str) -> Region:
    region = (
        session.query(Region)
        .filter(
            or_(
                func.lower(Region.si_gun_gu_name).like(f"%{keyword}%"),
                func.lower(Region.special_zone_name).like(f"%{keyword}%")
            )
        )
        .first()
    )
    if not region:
        raise ValueError(f"'{keyword}'에 해당하는 지역 정보를 찾을 수 없습니다.")
    return region

