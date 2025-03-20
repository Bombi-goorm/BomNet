package com.bombi.core.infrastructure.external.price.chart.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bombi.core.infrastructure.external.price.chart.dto.ChartNodeInfo;
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

	public List<ChartNodeInfo> getNodes(String item, String dateTime) {
		String query = "select * from kma.int_mafra__sankey_nodes"
			+ " where item = @item and date_time = @date_time"
			+ " order by node_id;";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.addNamedParameter("date_time", QueryParameterValue.date(dateTime))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<ChartNodeInfo> nodeInfos = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				long nodeId = value.get("node_id").getLongValue();
				String nodeName = value.get("node").getStringValue();

				ChartNodeInfo chartNodeInfo = new ChartNodeInfo(nodeId, nodeName);
				nodeInfos.add(chartNodeInfo);
			}

			return nodeInfos;
		} catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

}
