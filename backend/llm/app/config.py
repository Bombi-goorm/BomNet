from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    # ✅ 인증 관련
    AUTH_SERVER_URL: str
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

    # ✅ 환경설정 (env 파일 없이 환경변수만 사용 가능)
    model_config = SettingsConfigDict(env_file_encoding='utf-8')

# ✅ 환경변수 자동 매핑
settings = Settings()