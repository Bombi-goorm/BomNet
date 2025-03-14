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
public class PriceChartNodeApiClient {

	private final BigQuery bigQuery;

	public PriceChartNodeResponse sendChartNode() {
		String query = "select * from kma.int_mafra__sankey_node"
			+ " where node = @node"
			+ " order by node_id;";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			// .addNamedParameter("productNames", QueryParameterValue.array(productCodes.toArray(new String[0]), String.class))
			.addNamedParameter("node", QueryParameterValue.string("후지"))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			for (FieldValueList fieldValues : result.getValues()) {
				System.out.println("fieldValues = " + fieldValues);
			}

			return null;
		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
