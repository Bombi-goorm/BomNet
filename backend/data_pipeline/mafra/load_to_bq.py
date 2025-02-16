from google.cloud import bigquery
import json


def load_data_to_bigquery(jsonl_file, dataset_id, table_id, service_account_json):
    client = bigquery.Client.from_service_account_json(service_account_json)

    dataset_reference = bigquery.DatasetReference(client.project, dataset_id)
    table_reference = bigquery.TableReference(dataset_reference, table_id)

    with open(jsonl_file, 'r', encoding='utf-8') as f:
        rows = [json.loads(line) for line in f]
        client.insert_rows_json(table_reference, rows)




