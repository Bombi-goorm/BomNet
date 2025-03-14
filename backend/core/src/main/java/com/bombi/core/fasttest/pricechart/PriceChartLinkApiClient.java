package com.bombi.core.fasttest.pricechart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.StandardSQLTypeName;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PriceChartLinkApiClient {

	private final BigQuery bigQuery;

	public List<SankeyLinkDto> getChartLinkByNodeIds(List<String> targetIds, String date) {
		String query = "select"
			+ " n.node as nodeName,"
			+ " l.src_id as srcId, l.tgt_id as targetId, l.val as val"
			+ " from kma.int_mafra__sankey_link as l"
			+ " join kma.int_mafra__sankey_node as n"
			+ " on l.src_id = n.node_id"
			+ " where l.src_id IN UNNEST(@target_ids)"
			+ " and date_time = @date_time"
			+ " order by l.src_id";

		QueryParameterValue targetIdsParameterValues = QueryParameterValue.array(targetIds.toArray(new String[0]),
			StandardSQLTypeName.STRING);

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.setNamedParameters(Map.of("target_ids", targetIdsParameterValues))
			.addNamedParameter("date_time", QueryParameterValue.date(date))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<SankeyLinkDto> links = new ArrayList<>();
			for (FieldValueList value : result.getValues()) {
				String nodeName = value.get("nodeName").getStringValue();
				String srcId = value.get("srcId").getStringValue();
				String targetId = value.get("targetId").getStringValue();
				String val = String.valueOf(value.get("val").getLongValue());
				links.add(new SankeyLinkDto(nodeName, srcId, targetId, val));
			}
			return links;
		} catch (BigQueryException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	public List<SankeyLinkDto> getLinkByNodeNames(List<String> productList, String date) {
		String query = "select"
			+ " n.node as nodeName,"
			+ " l.src_id as srcId, l.tgt_id as targetId, l.val as val"
			+ " from kma.int_mafra__sankey_link as l"
			+ " join kma.int_mafra__sankey_node as n"
			+ " on l.src_id = n.node_id"
			+ " where n.node IN UNNEST(@nodeNames)"
			+ " and date_time = @date_time";

		QueryParameterValue targetIdsParameterValues = QueryParameterValue.array(productList.toArray(new String[0]),
			StandardSQLTypeName.STRING);

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.setNamedParameters(Map.of("nodeNames", targetIdsParameterValues))
			.addNamedParameter("date_time", QueryParameterValue.date(date))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<SankeyLinkDto> links = new ArrayList<>();
			for (FieldValueList value : result.getValues()) {
				String nodeName = value.get("nodeName").getStringValue();
				String srcId = value.get("srcId").getStringValue();
				String targetId = value.get("targetId").getStringValue();
				String val = String.valueOf(value.get("val").getLongValue());
				links.add(new SankeyLinkDto(nodeName, srcId, targetId, val));
			}
			return links;
		} catch (BigQueryException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
