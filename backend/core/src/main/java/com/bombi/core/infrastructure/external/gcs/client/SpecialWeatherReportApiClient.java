package com.bombi.core.infrastructure.external.gcs.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReport;
import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReportResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpecialWeatherReportApiClient {

	private static final String BUCKET_NAME = "kma_wrn";
	private static final String JSON_FILE_FORMAT = ".json";

	private final BigQuery bigQuery;
	private final ObjectMapper objectMapper;

	/**
	 * pnu코드를 기반으로 region테이블에서 stationId을 기반으로 조회
	 * @return
	 */
	public SpecialWeatherReportResponse sendSpecialWeatherReport(String stationId) {
		String query = "select station_id, title, fcst_date_time"
			+ " from kma.stg_kma__wrn"
			+ " where station_id = @stationId"
			+ " and fcst_date_time > @startTmFc"
			+ " and fcst_date_time < @endTmFc"
			+ " order by fcst_date_time desc"
			+ " limit 10";

		String today = getTodayDateTime();
		String tomorrow = getTomorrowDateTime();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("stationId", QueryParameterValue.string(stationId))
			.addNamedParameter("startTmFc", QueryParameterValue.string(today))
			.addNamedParameter("endTmFc", QueryParameterValue.string(tomorrow))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult result = bigQuery.query(queryConfig);

			List<SpecialWeatherReport> reports = extractWeatherReport(result);
			return new SpecialWeatherReportResponse(reports);

		} catch (Exception e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	private String getTodayDateTime() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(today, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private String getTomorrowDateTime() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0);
		LocalDateTime localDateTime = LocalDateTime.of(tomorrow, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private List<SpecialWeatherReport> extractWeatherReport(TableResult result) {
		List<SpecialWeatherReport> reports = new ArrayList<>();
		for (FieldValueList fieldValues : result.iterateAll()) {
			SpecialWeatherReport report = mapJsonToDto(fieldValues);
			reports.add(report);
		}
		return reports;
	}

	private SpecialWeatherReport mapJsonToDto(FieldValueList fieldValues) {
		String stnId = fieldValues.get("stnId").getStringValue();
		String title = fieldValues.get("title").getStringValue();
		String tmFc = fieldValues.get("tmFc").getStringValue();

		String titleString = title.replaceAll("\\d{4}\\.\\d{2}\\.\\d{2}\\.\\d{2}:\\d{2}", "");

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		LocalDateTime tmFcTime = LocalDateTime.parse(tmFc, dateTimeFormatter);

		return new SpecialWeatherReport(stnId, titleString, tmFcTime);
	}

}
