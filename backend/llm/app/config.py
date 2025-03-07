from dotenv import load_dotenv
import os

from pydantic import BaseSettings

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

settings = Settings()

# load_dotenv()
#
# OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')
