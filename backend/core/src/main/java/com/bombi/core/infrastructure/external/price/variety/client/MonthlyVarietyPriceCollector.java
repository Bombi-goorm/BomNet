package com.bombi.core.infrastructure.external.price.variety.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthlyVarietyPriceCollector {

	private final BigQuery bigQuery;

	/**
	 * 1년동안 품종별 평균 판매 가격 가져오기
	 * @param item      : 품목. ex) 사과
	 * @param startDate : 오늘부터 1년전
	 * @param endDate : 오늘
	 */
	@Cacheable(value = "MonthlyPrice", key = "#item")
	public List<VarietyPriceInfo> sendVarietyPriceTrend(String item, String startDate, String endDate) {
		String query = "SELECT"
			+ " variety, FORMAT_DATE('%Y-%m', date_time) as date, CAST(avg(avg_ppk) as INT64) as sumPrice"
			+ " FROM kma.int_mafra__variety_price_trend"
			+ " WHERE item = @item"
			+ " AND date_time >= @start_date"
			+ " AND date_time <= @end_date"
			+ " GROUP BY variety, FORMAT_DATE('%Y-%m', date_time)"
			+ " ORDER BY variety, FORMAT_DATE('%Y-%m', date_time)";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.addNamedParameter("start_date", QueryParameterValue.date(startDate))
			.addNamedParameter("end_date", QueryParameterValue.date(endDate))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<VarietyPriceInfo> varietyPriceInfos = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				String varietyValue = value.get("variety").getStringValue();
				String dateTimeValue = value.get("date").getStringValue();
				long averagePricePerKgValue = value.get("sumPrice").getLongValue();

				VarietyPriceInfo varietyPriceInfo = new VarietyPriceInfo(varietyValue, dateTimeValue, averagePricePerKgValue);

				varietyPriceInfos.add(varietyPriceInfo);
			}
			return varietyPriceInfos;
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
