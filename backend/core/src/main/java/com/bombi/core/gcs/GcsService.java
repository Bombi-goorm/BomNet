//package com.bombi.core.gcs;
//
//import org.springframework.stereotype.Service;
//
//import com.google.cloud.bigquery.BigQuery;
//import com.google.cloud.bigquery.FieldValueList;
//import com.google.cloud.bigquery.QueryJobConfiguration;
//import com.google.cloud.bigquery.TableResult;
//import com.google.cloud.storage.Blob;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class GcsService {
//
//	private final Storage storage;
//
//	public void queryBigQueryData() {
//		// 읽어올 버킷 이름과 객체(파일) 설정
//		String bucketName = "bomnet_auction";
//		String objectName = "20250220.jsonl";
//
//		// 버킷에서 객체(파일) 가져오기
//		Blob blob = storage.get(bucketName, objectName);
//		if (blob == null) {
//			System.out.println("버킷 '" + bucketName + "' 에 객체 '" + objectName + "' 이(가) 존재하지 않습니다.");
//			return;
//		}
//
//		// 객체 내용을 문자열로 변환하여 출력 (텍스트 파일일 경우)
//		String content = new String(blob.getContent());
//		System.out.println("파일 내용:");
//		System.out.println(content);
//	}
//
//}
