package com.bombi.core.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bombi.core.presentation.dto.price.QualityVarietyPriceData;
import com.google.cloud.bigquery.BigQuery;
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
	public List<QualityVarietyPriceData> sendVarietyPriceTrend(String item, String startDateTime, String endDateTime) {
		String query = "SELECT"
			// + " *"
			+ " variety, date_time,"
			+ " grd_nm, avg_ppk"
			+ " FROM kma.int_mafra__price_trend_by_grade"
			+ " WHERE item = @item"
			+ " AND grd_nm in ('특상', '상', '보통', '등외')"
			+ " AND date_time <= @endDateTime"
			+ " AND date_time >= @startDateTime"
			// + " GROUP BY variety, date_time"
			+ " ORDER BY variety, date_time";
			// + " LIMIT 20";

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("item", QueryParameterValue.string(item))
			.addNamedParameter("startDateTime", QueryParameterValue.string(startDateTime))
			.addNamedParameter("endDateTime", QueryParameterValue.string(endDateTime))
			.setUseLegacySql(false) // 표준 SQL 사용
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<QualityVarietyPriceData> qualityVarietyPriceData = new ArrayList<>();

			for (FieldValueList value : result.getValues()) {
				String varietyValue = value.get("variety").getStringValue();
				String dateTimeValue = value.get("date_time").getStringValue();
				String gradeName = value.get("grd_nm").getStringValue();
				long averagePricePerKg = value.get("avg_ppk").getLongValue();

				QualityVarietyPriceData data = new QualityVarietyPriceData(varietyValue, dateTimeValue, gradeName, averagePricePerKg);
				qualityVarietyPriceData.add(data);
			}
			return qualityVarietyPriceData;
		}
		catch (InterruptedException e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}
}
