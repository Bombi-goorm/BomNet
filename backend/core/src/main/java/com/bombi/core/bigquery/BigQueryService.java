package com.bombi.core.bigquery;

import org.springframework.stereotype.Service;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BigQueryService {

	private final BigQuery bigQuery;

	public void queryBigQueryData() {
		String query = "SELECT * FROM `goorm-bomnet.mafra.auction` LIMIT 10";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);
			for (FieldValueList row : result.iterateAll()) {
				System.out.println("row = " + row);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

}
