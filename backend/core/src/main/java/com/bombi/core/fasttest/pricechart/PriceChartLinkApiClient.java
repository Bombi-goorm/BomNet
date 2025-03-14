package com.bombi.core.fasttest.pricechart;

import org.springframework.stereotype.Component;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceChartLinkApiClient {

	private final BigQuery bigQuery;

	public PriceChartLinkResponse sendChartLink() {
		String query = "select * from kma.int_mafra__sankey_link"
			+ " where src_id = @src_id"
			+ " and date_time = @date_time;";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("src_id", QueryParameterValue.int64(908))
			.addNamedParameter("date_time", QueryParameterValue.date("2025-03-07"))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			System.out.println("fieldValueList : ");
			for (FieldValueList fieldValues : result.getValues()) {
				System.out.println("fieldValues = " + fieldValues);
			}

			return null;
		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	public void getChartLinkAndNode() {
		String query = "select * from kma.int_mafra__sankey_link as l"
			+ " join kma.int_mafra__sankey_node as n"
			+ " on l.src_id = n.node_id"
			+ " where src_id = @src_id"
			+ " and date_time = @date_time;";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("src_id", QueryParameterValue.int64(908))
			.addNamedParameter("date_time", QueryParameterValue.date("2025-03-07"))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			System.out.println("fieldValueList : ");
			for (FieldValueList fieldValues : result.getValues()) {
				System.out.println("fieldValues = " + fieldValues);
			}

			// return null;
		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
