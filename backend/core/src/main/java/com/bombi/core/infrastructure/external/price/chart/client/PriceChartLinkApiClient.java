package com.bombi.core.infrastructure.external.price.chart.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bombi.core.infrastructure.external.price.chart.dto.ChartLinkInfo;
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

	public List<ChartLinkInfo> getLinks(String item, String dateTime) {

		String query = "select"
			+ " *"
			+ " from kma.int_mafra__sankey_links"
			+ " where item = @item and date_time = @date_time";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.addNamedParameter("date_time", QueryParameterValue.date(dateTime))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<ChartLinkInfo> linkInfos = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				long src = value.get("src").getLongValue();
				long target = value.get("tgt").getLongValue();
				String val = value.get("val").getStringValue();

				linkInfos.add(new ChartLinkInfo(src, target, val));
			}
			return linkInfos;
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
