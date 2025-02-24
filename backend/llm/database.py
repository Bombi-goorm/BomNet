from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

# ✅ MySQL 데이터베이스 연결 설정
DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"

# ✅ SQLAlchemy 엔진 및 세션 팩토리 생성
engine = create_engine(DATABASE_URL, pool_recycle=3600, pool_size=10)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()


# ✅ DB 세션 종속성 주입
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
