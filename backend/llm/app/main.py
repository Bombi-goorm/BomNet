from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from app.gpt import router as gpt_router
from app.config import OPENAI_API_KEY

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.exc import SQLAlchemyError

# Database connection setup
DATABASE_URL = "mysql+pymysql://root:1234@mariadb:3307/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Declare a base class for the models
Base = declarative_base()


# FastAPI app setup
app = FastAPI()
# Request model
class UserInput(BaseModel):
    user_input: str

app.include_router(gpt_router, prefix="/gpt", tags=["gpt"])
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],  # Your frontend's origin
    allow_credentials=True,
    allow_methods=["*"],  # Allow all methods (GET, POST, etc.)
    allow_headers=["*"],  # Allow all headers
)
@app.get("/")
def hello():
    return {"message": "Hello World!"}
