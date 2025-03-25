from fastapi import APIRouter
from google.cloud import bigquery
import logging

from app.config import settings
from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto
from app.dto.response_dto import WeatherResponseDto, WeatherInfo

weather_router = APIRouter()
logger = logging.getLogger("weather_logger")

# BigQuery 클라이언트 초기화
client = bigquery.Client(project=settings.GCP_PROJECT_ID)

# GCP 프로젝트 및 데이터셋 설정
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
        # BigQuery 쿼리 정의
        query = """
            SELECT
                fcst_date_time,
                TMP as temperature,
                WSD as windSpeed,
                SKY as skyStatus,
                REH as humidity
            FROM `goorm-bomnet.kma.int_kma_pivoted_short`
            WHERE nx = @nx AND ny = @ny
            ORDER BY fcst_date_time
            LIMIT 10
        """

        # BigQuery 쿼리 파라미터 설정
        job_config = bigquery.QueryJobConfig(
            query_parameters=[
                bigquery.ScalarQueryParameter("nx", "STRING", data.nx),  # 사용자 입력값
                bigquery.ScalarQueryParameter("ny", "STRING", data.ny)  # 사용자 입력값
            ]
        )

        # 쿼리 실행
        query_job = client.query(query, job_config=job_config)
        result = query_job.result()

        # 첫 번째 결과 행을 가져옴
        row = next(result, None)
        if row:
            # 실제 데이터를 바탕으로 WeatherResponseDto 생성
            weather_info = WeatherResponseDto(
                location=data.region,
                weatherInfo=WeatherInfo(
                    weather=row.skyStatus,
                    temperature=row.temperature,
                    humidity=row.humidity,
                    wind=row.windSpeed,
                    dateTime=row.fcst_date_time.isoformat() + "Z"
                )
            )
            return CommonResponseDto(
                status="200",
                message="날씨 정보 조회 성공",
                data=weather_info
            )
        else:
            logger.warning(f"[날씨 조회 실패] 데이터 없음 - region={data.region}, nx={data.nx}, ny={data.ny}")
            return CommonResponseDto(
                status="404",
                message=f"{data.region}에 대한 날씨 데이터를 찾을 수 없습니다.",
                data=None
            )

    except Exception as e:
        logger.error(f"[ERROR] BigQuery 연결 실패 - region={data.region}", exc_info=True)
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
