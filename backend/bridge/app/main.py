from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app. import router as bridge_router
import uvicorn

app = FastAPI()

# Router 등록
app.include_router(bridge_router, prefix="/bridge", tags=["bridge"])

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8183)

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  # 프론트엔드 도메인
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 헤더 허용
)
