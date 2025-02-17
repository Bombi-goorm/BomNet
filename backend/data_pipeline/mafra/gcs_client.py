from google.cloud import storage
from utils import generate_filename


class GCSClient:
    def __init__(self, bucket_name, service_account_json):
        self.storage_client = storage.Client.from_service_account_json(service_account_json)
        self.bucket = self.storage_client.bucket(bucket_name)

    def upload_jsonl(self, jsonl_data, prefix=""):
        file_name = generate_filename(prefix)
        blob = self.bucket.blob(file_name)
        blob.upload_from_string(jsonl_data, content_type="application/jsonl")
        print(f"✅ 파일 업로드 완료: gs://{self.bucket.name}/{file_name}")


