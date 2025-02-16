import os
from api_client import APIClient
from file_utils import write_to_jsonl
from load_to_bq import load_data_to_bigquery
import config


def main():
    params = {
        "SALEDATE": "20250214",
        "WHSALCD": "110001",
        "CMPCD": "11000104",
    }

    api_client = APIClient(config.BASE_URL, config.API_KEY, config.API_URL, config.START_INDEX, config.END_INDEX, params)
    response_data = api_client.send_request()

    if response_data:
        write_to_jsonl(response_data, config.OUTPUT_PATH, config.API_URL)

        load_data_to_bigquery(config.OUTPUT_PATH, config.DATASET_ID, config.TABLE_ID, config.SERVICE_ACCOUNT_PATH)
    else:
        print("API 요청 실패")


if __name__ == "__main__":
    main()
