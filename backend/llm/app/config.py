import os
from dotenv import load_dotenv
from pydantic_settings import BaseSettings

# ✅ .env 파일 강제 로드
dotenv_path = os.path.join(os.path.dirname(__file__), "../.env")
load_dotenv(dotenv_path)  # ✅ 강제 로드

class Settings(BaseSettings):
    # ✅ 인증 관련
    AUTH_SERVER_URL: str
    JWT_PUBLIC_KEY_PATH: str
    JWT_SECRET: str
    JWT_ALGORITHM: str

    # ✅ 데이터베이스 관련
    DATABASE_USER: str
    DATABASE_PASSWORD: str
    DATABASE_HOST: str
    DATABASE_PORT: int
    DATABASE_NAME: str

    # ✅ Google Cloud 관련
    OPENAI_API_KEY: str
    GOOGLE_APPLICATION_CREDENTIALS: str
    GCP_PROJECT_ID: str
    DATASET_ID: str
    TABLE_ID: str

    class Config:
        env_file = "../.env"  # ✅ .env 파일을 명시적으로 지정
        env_file_encoding = "utf-8"

# ✅ 환경변수 로드 확인
settings = Settings()