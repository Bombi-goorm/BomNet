from fastapi import FastAPI, APIRouter
from fastapi.middleware.cors import CORSMiddleware

from app.auth.jwt_filter import JwtFilter
from app.config import settings
from app.controller.alert_controller import alert_router
from app.controller.base_controller import base_router
from app.controller.other_controller import other_router
from app.controller.price_controller import price_router
# from app.controller.weather_controller import weather_router
from app.database import engine, Base

# FastAPI 앱 생성
app = FastAPI()

# ✅ JWT 미들웨어 추가 (⚡ 올바른 방식)
app.add_middleware(JwtFilter)

# DB 테이블 생성
Base.metadata.create_all(bind=engine)

# ✅ CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["https://bomnet.shop"],  # 프론트엔드 도메인
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"], # 모든 헤더 허용
)

# 공통 라우터 생성
router = APIRouter(prefix="/llm")
router.include_router(base_router, prefix="/base", tags=["Base"])
router.include_router(other_router, prefix="/other", tags=["Other"])
# router.include_router(weather_router, prefix="/weather", tags=["Weather"])
router.include_router(price_router, prefix="/price", tags=["Price"])
router.include_router(alert_router, prefix="/alert", tags=["Alert"])

# 공통 라우터 등록
app.include_router(router)

app.include_router(router, prefix="/llm", tags=["llm"])

# FastAPI 실행
# uvicorn app.main:app --host 0.0.0.0 --port 8182 --reload
if __name__ == "__main__":
    import uvicorn

    # ✅ 인증 관련
    print(f"AUTH_SERVER_URL     :: {settings.AUTH_SERVER_URL}")
    print(f"JWT_SECRET (preview):: {settings.JWT_SECRET[:10]}... ({len(settings.JWT_SECRET)} chars)")
    print(f"JWT_ALGORITHM       :: {settings.JWT_ALGORITHM}")

    # ✅ 데이터베이스 관련
    print(f"DATABASE_USER       :: {settings.DATABASE_USER}")
    print(f"DATABASE_PASSWORD   :: {settings.DATABASE_PASSWORD[:3]}*** (length {len(settings.DATABASE_PASSWORD)} chars)")
    print(f"DATABASE_HOST       :: {settings.DATABASE_HOST}")
    print(f"DATABASE_PORT       :: {settings.DATABASE_PORT}")
    print(f"DATABASE_NAME       :: {settings.DATABASE_NAME}")

    uvicorn.run("app:main:app", host="0.0.0.0", port=8182, reload=True)
