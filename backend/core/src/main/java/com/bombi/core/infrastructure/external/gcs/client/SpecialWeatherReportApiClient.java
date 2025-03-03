package com.bombi.core.infrastructure.external.gcs.client;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReport;
import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;
import com.google.cloud.storage.Blob;

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
		String query = "select stnId, title, tmFc"
			+ " from kma.wrn"
			+ " where stnId = @stnId"
			+ " and tmFc > @startTmFc"
			+ " and tmFc < @endTmFc" 
			+ " order by tmFc desc"
			+ " limit 10";

		int stnId = Integer.parseInt(stationId);
		long today = getTodayAsInt();
		long tomorrow = getTomorrowAsInt();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("stnId", QueryParameterValue.int64(stnId))
			.addNamedParameter("startTmFc", QueryParameterValue.int64(today))
			.addNamedParameter("endTmFc", QueryParameterValue.int64(tomorrow))
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

	private long getTodayAsInt() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0);
		LocalDateTime localDateTime = LocalDateTime.of(today, localTime);
		String todayStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddhhmm"));
		return Long.parseLong(todayStr);
	}

	private long getTomorrowAsInt() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0);
		LocalDateTime localDateTime = LocalDateTime.of(tomorrow, localTime);
		String tomorrowStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddhhmm"));
		return Long.parseLong(tomorrowStr);
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
