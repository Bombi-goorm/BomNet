package com.bombi.core.infrastructure.external.weather.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;
import com.bombi.core.infrastructure.external.weather.dto.WeatherForecastResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValue;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherForecastApiClient {

	private final BigQuery bigQuery;
	private final ObjectMapper objectMapper;

	public WeatherForecastResponse sendWeatherForecast() {

		String query = "SELECT"
			+ " fcstTime As forecastTime,"
			+ " fcstDate as forecastDate,"
			+ "	ARRAY_AGG(DISTINCT TO_JSON_STRING(STRUCT(category As forecastType, fcstValue As forecastDescription))) As forecastData"
			+ " FROM `goorm-bomnet.kma.short_fact`"
			+ " WHERE fcstDate = @fcstDate"
			+ " AND category IN ('SKY', 'TMP', 'WSD', 'REH')"
			+ " AND fcstTime BETWEEN CAST(FORMAT_TIMESTAMP('%H%M', CURRENT_TIMESTAMP()) AS INT64)"
			+ " 		AND CAST(FORMAT_TIMESTAMP('%H%M', TIMESTAMP_ADD(CURRENT_TIMESTAMP(), INTERVAL 6 HOUR)) AS INT64)"
			+ " AND nx = 38 AND ny = 53"
			+ " AND baseDate = @baseDate"
			+ " GROUP BY fcstTime, fcstDate"
			+ " ORDER BY fcstTime;";

		int today = getTodayAsInt();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("fcstDate", QueryParameterValue.int64(today))
			.addNamedParameter("baseDate", QueryParameterValue.int64(today))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult tableResult = bigQuery.query(queryConfig);

			Map<LocalDateTime, List<ForecastInfoDto>> resultMap = new HashMap<>();

			for (FieldValueList fieldValues : tableResult.iterateAll()) {

				LocalDateTime forecastLocalDateTime = convertKeyToDateTime(fieldValues);

				FieldValue forecastData = fieldValues.get("forecastData");
				for (FieldValue fieldValue : forecastData.getRepeatedValue()) {
					ForecastInfoDto forecastInfoDto = mapJsonToDto(fieldValue.getStringValue());
					resultMap.computeIfAbsent(forecastLocalDateTime, k -> new ArrayList<>()).add(forecastInfoDto);
				}
			}

			return new WeatherForecastResponse(resultMap);
		} catch (Exception e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	public int getTodayAsInt() {
		LocalDate today = LocalDate.now();
		String todayStr = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return Integer.parseInt(todayStr);
	}

	private LocalDateTime convertKeyToDateTime(FieldValueList fieldValues) {
		String forecastTime = fieldValues.get("forecastTime").getStringValue();
		String forecastDate = fieldValues.get("forecastDate").getStringValue();
		String forecastDateTime = forecastDate + forecastTime;

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

		return LocalDateTime.parse(forecastDateTime, dateTimeFormatter);
	}

	public ForecastInfoDto mapJsonToDto(String fieldValueString) {
		try {
			return objectMapper.readValue(fieldValueString, ForecastInfoDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 매핑 실패");
		}
	}

}
