from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.gpt import router as gpt_router  # 모듈 경로가 맞는지 확인

# FastAPI app setup
app = FastAPI()

# Router 등록
app.include_router(gpt_router, prefix="/gpt", tags=["gpt"])

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  # 프론트엔드 도메인
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 헤더 허용
)

