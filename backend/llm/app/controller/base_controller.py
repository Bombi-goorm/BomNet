from fastapi import APIRouter
from sqlalchemy import create_engine, text
from sqlalchemy.exc import SQLAlchemyError
from sqlalchemy.orm import sessionmaker

base_router = APIRouter()


DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)


@base_router.get("/health")
def health():
    return {"LLM :: Healthy"}


@base_router.get("/ping")
def ping_db():
    try:
        db = SessionLocal()
        db.execute(text('SELECT 1'))  # Simple query to check DB connection
        return {"message": "Database connection successful"}
    except SQLAlchemyError as e:
        return {"error": str(e)}
    finally:
        db.close()
