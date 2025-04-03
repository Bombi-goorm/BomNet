from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy import text
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import Session

from app.database import get_db

base_router = APIRouter()


@base_router.get("/health")
def health():
    return {"LLM :: Healthy"}


@base_router.get("/ping")
def ping_db(db: Session = Depends(get_db)):
    try:
        db.execute(text("SELECT 1"))  # DB 연결 확인용 간단한 쿼리 실행
        return {"message": "Database connection successful"}
    except SQLAlchemyError as e:
        raise HTTPException(status_code=500, detail=str(e))
