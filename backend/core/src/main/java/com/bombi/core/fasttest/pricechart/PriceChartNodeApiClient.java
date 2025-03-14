package com.bombi.core.fasttest.pricechart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.StandardSQLTypeName;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceChartNodeApiClient {

	private final BigQuery bigQuery;

	public List<ChartNodeInfo> getNodeInfoByNodeNames(List<String> productNames) {
		String query = "select * from kma.int_mafra__sankey_node"
			+ " where node IN UNNEST(@nodeNames)"
			+ " order by node_id;";

		QueryParameterValue productNameParams = QueryParameterValue.array(productNames.toArray(new String[0]),
			StandardSQLTypeName.STRING);

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.setNamedParameters(Map.of("nodeNames", productNameParams))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		return convertTableResultToNodeInfo(queryConfig);
	}

	public List<ChartNodeInfo> getNodeInfoByNodeIds(List<String> marketIds) {
		String query = "select * from kma.int_mafra__sankey_node"
			+ " where node_id IN UNNEST(@nodeIds)"
			+ " order by node_id;";

		QueryParameterValue marketIdsParams = QueryParameterValue.array(marketIds.toArray(new String[0]),
			StandardSQLTypeName.STRING);

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.setNamedParameters(Map.of("nodeIds", marketIdsParams))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		return convertTableResultToNodeInfo(queryConfig);
	}

	private List<ChartNodeInfo> convertTableResultToNodeInfo(QueryJobConfiguration queryConfig) {
		try {
			TableResult result = bigQuery.query(queryConfig);

			List<ChartNodeInfo> infos = new ArrayList<>();
			for (FieldValueList fieldValues : result.getValues()) {
				String nodeId = fieldValues.get("node_id").getStringValue();
				String node = fieldValues.get("node").getStringValue();
				infos.add(new ChartNodeInfo(nodeId, node));
			}
			return infos;

		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
