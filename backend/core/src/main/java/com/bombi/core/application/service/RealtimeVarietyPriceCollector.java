package com.bombi.core.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.bombi.core.common.annotation.CacheableData;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RealtimeVarietyPriceCollector {

	private final BigQuery bigQuery;

	/**
	 * 실시간 품종별 평균 판매 가격 가져오기
	 * @param item      : 품목. ex) 사과
	 * @param startDate : 오늘부터 30일 전
	 * @param endDate : 오늘
	 */
	// @Cacheable(value = "RealTimePrice", key = "#item + '_' + #endDate")
	@CacheableData
	public List<VarietyPriceInfo> sendVarietyPriceTrend(String item, String startDate, String endDate) {
		String query = "SELECT"
			+ " whsl_mrkt_nm, variety, FORMAT_DATE('%Y-%m-%d %H:%M', DATETIME_TRUNC(date_time, HOUR)) as date, CAST(AVG(rt_price) AS INT64) as average_rt_price"
			+ " FROM kma.stg_mafra__real_time"
			+ " WHERE item = @item"
			+ " AND date_time >= @start_date"
			+ " AND date_time <= @end_date"
			+ " GROUP BY whsl_mrkt_nm, variety, FORMAT_DATE('%Y-%m-%d %H:%M', DATETIME_TRUNC(date_time, HOUR))"
			+ " ORDER BY whsl_mrkt_nm, variety, FORMAT_DATE('%Y-%m-%d %H:%M', DATETIME_TRUNC(date_time, HOUR))";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.addNamedParameter("market", QueryParameterValue.string("서울가락"))
			.addNamedParameter("start_date", QueryParameterValue.string(startDate))
			.addNamedParameter("end_date", QueryParameterValue.string(endDate))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<VarietyPriceInfo> varietyPriceInfos = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				String market = value.get("whsl_mrkt_nm").getStringValue();
				String varietyValue = value.get("variety").getStringValue();
				String dateTimeValue = value.get("date").getStringValue();
				long price = value.get("average_rt_price").getLongValue();

				VarietyPriceInfo varietyPriceInfo = new VarietyPriceInfo(market, varietyValue, dateTimeValue, price);

				varietyPriceInfos.add(varietyPriceInfo);
			}
			return varietyPriceInfos;
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

}
