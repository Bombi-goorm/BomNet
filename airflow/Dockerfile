FROM apache/airflow:2.5.1
USER root
RUN pip install --no-cache-dir pymysql
USER airflow
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
