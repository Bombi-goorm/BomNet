from fastapi import FastAPI, APIRouter
from fastapi.middleware.cors import CORSMiddleware


# FastAPI 앱 생성
app = FastAPI()

router = APIRouter()

app.include_router(router, prefix="/llm", tags=["llm"])

# CORS 설정
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  # 프론트엔드 도메인
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"],  # 모든 헤더 허용
)

# 기본 헬스체크 엔드포인트
@app.get("/")
def root():
    return {"message": "FastAPI is running"}

# FastAPI 실행
if __name__ == "__main__":
    import uvicorn
    uvicorn.run("main:app", host="0.0.0.0", port=8182, reload=True)