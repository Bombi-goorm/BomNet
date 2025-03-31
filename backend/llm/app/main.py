from typing import List

from fastapi import FastAPI, APIRouter
from fastapi.middleware.cors import CORSMiddleware
import logging

from sqlalchemy import text
from sqlalchemy.exc import SQLAlchemyError

from app.auth.jwt_filter import JwtFilter
from app.controller.alert_controller import alert_router
from app.controller.base_controller import base_router
from app.controller.other_controller import other_router
from app.controller.price_controller import price_router
from app.controller.weather_controller import weather_router
from app.database import engine, Base
from app.util.request_interceptor import RequestTimerMiddleware

# 사용 예시
logger_names = [
    "alert_logger",
    "weather_logger",
    "gpt_logger",
    "auth_logger",
    "database_logger",
    "request_timer_logger",
    "main_logger",
]


def setup_loggers(logger_names: List[str]):
    formatter = logging.Formatter("%(asctime)s | %(name)s | %(levelname)s | %(message)s")

    for name in logger_names:
        logger = logging.getLogger(name)
        logger.setLevel(logging.INFO)

        # 중복 방지: 핸들러가 없을 때만 등록
        if not logger.handlers:
            handler = logging.StreamHandler()
            handler.setFormatter(formatter)
            logger.addHandler(handler)


setup_loggers(logger_names)

logger = logging.getLogger("main_logger")

# FastAPI 앱 생성
app = FastAPI()

# ✅ 미들웨어 추가
app.add_middleware(JwtFilter)
app.add_middleware(RequestTimerMiddleware)

# DB 테이블 생성
# Base.metadata.create_all(bind=engine)

# ✅ CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["https://bomnet.shop", "https://auth.bomnet.shop", "https://core.bomnet.shop", "https://llm.bomnet.shop"],
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 헤더 허용
)

# 공통 라우터 생성
router = APIRouter(prefix="/llm")
router.include_router(base_router, prefix="/base", tags=["Base"])
router.include_router(other_router, prefix="/other", tags=["Other"])
router.include_router(weather_router, prefix="/weather", tags=["Weather"])
router.include_router(price_router, prefix="/price", tags=["Price"])
router.include_router(alert_router, prefix="/alert", tags=["Alert"])

# 공통 라우터 등록
app.include_router(router)

app.include_router(router, prefix="/llm", tags=["llm"])


@app.on_event("startup")
async def check_db_connection_on_startup():
    logger.info("[서버 시작] DB 연결 확인 중...")
    try:
        with engine.connect() as conn:
            conn.execute(text("SELECT 1"))
        logger.info("[DB 연결 성공] DB 연결 완료")
    except SQLAlchemyError as e:
        logger.error("[ERROR] DB 연결 실패", exc_info=True)


# FastAPI 실행
# uvicorn app.main:app --host 0.0.0.0 --port 8182 --reload
if __name__ == "__main__":
    import uvicorn

    uvicorn.run("app:main:app", host="0.0.0.0", port=8182, reload=True)
