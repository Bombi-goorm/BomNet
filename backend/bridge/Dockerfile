FROM python:3.12-slim

WORKDIR /app

COPY requirements.txt .

RUN pip install --upgrade -r requirements.txt

COPY . .

EXPOSE 8182

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8100"]