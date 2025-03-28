package com.bombi.core.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.bombi.core.presentation.dto.price.QualityChartData;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QualityVarietyPriceCollector {

	private final BigQuery bigQuery;

	/**
	 * 품질별 품종별 판매 가격 가져오기
	 *
	 * @param item : 품목. ex) 사과
	 */
	@Cacheable(value = "ProductChart", key = "'quality_' + #item + '_' + #startDateTime")
	public List<QualityChartData> sendVarietyPriceTrend(String item, String startDateTime, String endDateTime) {
		String query = "SELECT"
			+ " variety, date_time,"
			+ " MAX(IF(grd_nm = '특상',   avg_ppk, NULL)) AS special,"
			+ " MAX(IF(grd_nm = '상',   avg_ppk, NULL)) AS high,"
			+ " MAX(IF(grd_nm = '보통',   avg_ppk, NULL)) AS moderate,"
			+ " MAX(IF(grd_nm = '등외', avg_ppk, NULL)) AS other"
			+ " FROM kma.int_mafra__price_trend_by_grade"
			+ " WHERE item = @item"
			+ " AND date_time <= @endDateTime"
			+ " AND date_time >= @startDateTime"
			+ " GROUP BY variety, date_time"
			+ " ORDER BY variety, date_time";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.addNamedParameter("startDateTime", QueryParameterValue.string(startDateTime))
			.addNamedParameter("endDateTime", QueryParameterValue.string(endDateTime))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<QualityChartData> totalData = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				String varietyValue = value.get("variety").getStringValue();
				String dateTimeValue = value.get("date_time").getStringValue();
				Long specialPrice = extractFromFieldValue(value, "special");
				Long highPrice = extractFromFieldValue(value, "high");
				Long moderatePrice = extractFromFieldValue(value, "moderate");
				Long otherPrice = extractFromFieldValue(value, "other");

				QualityChartData data = new QualityChartData(dateTimeValue, varietyValue , specialPrice, highPrice,
					moderatePrice, otherPrice);

				totalData.add(data);
			}
			return totalData;
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	private Long extractFromFieldValue(FieldValueList value, String fieldName) {
		FieldValue specialFieldValue = value.get(fieldName);
		if (specialFieldValue.isNull()) {
			return null;
		}
		return specialFieldValue.getLongValue();
	}
}
