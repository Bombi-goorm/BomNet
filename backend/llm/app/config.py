from pydantic_settings import BaseSettings

from dotenv import load_dotenv
import os


class Settings(BaseSettings):
    OPENAI_API_KEY: str
    GOOGLE_APPLICATION_CREDENTIALS: str
    GCP_PROJECT_ID: str
    DATASET_ID: str
    TABLE_ID: str
    AUTH_SERVER_URL: str
    JWT_PUBLIC_KEY_PATH: str
    JWT_SECRET: str
    JWT_ALGORITHM: str
    DATABASE_USER: str
    DATABASE_PASSWORD: str
    DATABASE_HOST: str
    DATABASE_PORT: str
    DATABASE_NAME: str


    class Config:
        env_file = ".env"  # .env 파일을 명시적으로 설정
        env_file_encoding = "utf-8"  # 파일 인코딩 설정

settings = Settings()

# load_dotenv()
#
# OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')
