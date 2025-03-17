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

	private final BigQuery bigQuery;

	/**
	 * pnu코드를 기반으로 region테이블에서 stationId을 기반으로 조회
	 * @return
	 */
	public SpecialWeatherReportResponse sendSpecialWeatherReport() {
		String query = "select stn_nm, title, fcst_date_time"
			+ " from kma.int_kma__wrn_alarm"
			+ " where fcst_date_time > @startTmFc"
			+ " and fcst_date_time < @endTmFc"
			+ " order by fcst_date_time desc"
			+ " limit 6";

		String today = getTodayDateTime();
		String tomorrow = getTomorrowDateTime();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
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
		String stationName = fieldValues.get("stn_nm").getStringValue();
		String title = fieldValues.get("title").getStringValue();
		String tmFc = fieldValues.get("fcst_date_time").getStringValue();

		String regex = "^\\[특보\\]\\s+제[\\d-]+호\\s+:\\s+\\d{4}\\.\\d{2}\\.\\d{2}\\.\\d{2}:\\d{2}\\s*\\/\\s*";
		String titleString = title.replaceAll(regex, "");

		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(tmFc, inputFormatter);

		return new SpecialWeatherReport(stationName, titleString, dateTime);
	}


}
