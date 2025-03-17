import os
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

from app.config import settings

# DATABASE_USER = os.environ["DATABASE_USER"]
# DATABASE_PASSWORD = os.environ["DATABASE_PASSWORD"]
# DATABASE_HOST = os.environ["DATABASE_HOST"]
# DATABASE_PORT = os.environ["DATABASE_PORT"]
# DATABASE_NAME = os.environ["DATABASE_NAME"]

# SQLAlchemy 연결 URL 구성
# DATABASE_URL = f"mysql+pymysql://{DATABASE_USER}:{DATABASE_PASSWORD}@{DATABASE_HOST}:{DATABASE_PORT}/{DATABASE_NAME}"
DATABASE_URL = f"mysql+pymysql://{settings.DATABASE_USER}:{settings.DATABASE_PASSWORD}@{settings.DATABASE_HOST}:{settings.DATABASE_PORT}/{settings.DATABASE_NAME}"


# ✅ DB 세션 종속성 주입
engine = create_engine(
    DATABASE_URL,
    pool_size=10,
    max_overflow=20,
    pool_pre_ping=True,    # 커넥션 살아있는지 확인
    pool_recycle=3600      # 1시간 지나면 재활용
)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()

# DB 세션 종속성 주입
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
