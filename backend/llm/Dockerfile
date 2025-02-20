# Python 3.9 기반 이미지 사용
FROM python:3.12-slim

# 작업 디렉토리 설정
WORKDIR /app

# 현재 디렉토리의 모든 파일을 /app 폴더에 복사
COPY . /app

# requirements.txt 복사 후 패키지 설치
RUN pip install --no-cache-dir -r requirements.txt

# 환경 변수 설정
ENV PYTHONPATH="/app"

# 컨테이너에서 사용할 포트
EXPOSE 8182

# FastAPI 앱 실행
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
