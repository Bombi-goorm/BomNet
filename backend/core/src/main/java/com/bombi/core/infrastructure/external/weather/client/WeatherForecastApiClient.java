package com.bombi.core.infrastructure.external.weather.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;
import com.bombi.core.infrastructure.external.weather.dto.WeatherForecastResponse;
import com.bombi.core.presentation.dto.home.WeatherInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherForecastApiClient {

	private final BigQuery bigQuery;
	private final ObjectMapper objectMapper;

	/**
	 * 단기 예보 조회
	 * @return
	 */
	public WeatherForecastResponse sendWeatherForecast() {

		String query = "SELECT"
			+ " *"
			+ " FROM `goorm-bomnet.kma.int_kma_pivoted_short`"
			// + " WHERE fcstTime >= @startFcstTime and fcstTime <= @endFcstTime"
			+ " WHERE nx = 38 AND ny = 53"
			+ " ORDER BY fcstTime"
			+ " LIMIT 30";

		int endTime = getTimeAsInt(LocalTime.now());
		int startTime = getTimeAsInt(LocalTime.now().minusHours(6L));

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			// .addNamedParameter("startFcstTime", QueryParameterValue.int64(startTime))
			// .addNamedParameter("endFcstTime", QueryParameterValue.int64(endTime))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult tableResult = bigQuery.query(queryConfig);

			List<WeatherInfo> weatherInfos = new ArrayList<>();
			for (FieldValueList fieldValues : tableResult.iterateAll()) {
				String forecastTime = fieldValues.get("fcstTime").getStringValue();
				forecastTime = String.format("%04d", Integer.parseInt(forecastTime));

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
				LocalTime time = LocalTime.parse(forecastTime, formatter);

				LocalDate now = LocalDate.now();

				LocalDateTime forecastDateTime = LocalDateTime.of(now, time);

				String temperature = fieldValues.get("TMP").getStringValue();
				String windSpeed = fieldValues.get("WSD").getStringValue();
				String skyStatus = fieldValues.get("SKY").getStringValue();
				String humidity = fieldValues.get("REH").getStringValue();

				WeatherInfo weatherInfo = new WeatherInfo(forecastDateTime, skyStatus, temperature, humidity, windSpeed);
				weatherInfos.add(weatherInfo);
			}

			return new WeatherForecastResponse(weatherInfos);
		} catch (Exception e) {
			throw new RuntimeException("BigQuery 쿼리 실행 중 오류 발생", e);
		}
	}

	private int getTimeAsInt(LocalTime time) {
		String timeStr = time.format(DateTimeFormatter.ofPattern("HHmm"));
		return Integer.parseInt(timeStr);
	}

	private ForecastInfoDto mapJsonToDto(String fieldValueString) {
		try {
			return objectMapper.readValue(fieldValueString, ForecastInfoDto.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 매핑 실패");
		}
	}

}
