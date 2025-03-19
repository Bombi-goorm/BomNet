package com.bombi.core.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bombi.core.presentation.dto.price.RegionChartData;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegionVarietyPriceCollector {

	private final BigQuery bigQuery;

	/**
	 * 지역별 품종별 판매 가격 가져오기
	 *
	 * @param item : 품목. ex) 사과
	 */
	public List<RegionChartData> sendVarietyPriceTrend(String item) {
		String query = "SELECT"
			+ " plor_nm, date_time, variety, avg_ppk"
			+ " FROM kma.int_mafra__price_by_plor"
			+ " WHERE item = @item"
			+ " ORDER BY variety, date_time, plor_nm";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<RegionChartData> regionChartData = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				String varietyValue = value.get("variety").getStringValue();
				String dateTimeValue = value.get("date_time").getStringValue();
				String regionName = value.get("plor_nm").getStringValue();
				long averagePricePerKg = value.get("avg_ppk").getLongValue();

				// System.out.print("varietyValue = " + varietyValue);
				// System.out.print(", dateTimeValue = " + dateTimeValue);
				// System.out.print(", regionName = " + regionName);
				// System.out.println(", averagePricePerKg = " + averagePricePerKg);

				RegionChartData data = new RegionChartData(varietyValue, regionName, averagePricePerKg);
				regionChartData.add(data);
			}
			return regionChartData;
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
