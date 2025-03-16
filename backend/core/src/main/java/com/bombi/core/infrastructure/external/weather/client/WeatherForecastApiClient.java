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
import com.google.cloud.bigquery.QueryParameterValue;
import com.google.cloud.bigquery.TableResult;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherForecastApiClient {

	private final BigQuery bigQuery;

	/**
	 * 단기 예보 조회
	 * @return
	 */
	public WeatherForecastResponse sendWeatherForecast() {

		String query = "SELECT"
			+ " *"
			+ " FROM `goorm-bomnet.kma.int_kma_pivoted_short`"
			// + " WHERE fcst_date_time >= @startFcstTime and fcst_date_time <= @endFcstTime"
			+ " WHERE nx = @nx AND ny = @ny"
			+ " ORDER BY fcst_date_time"
			+ " LIMIT 10";

		String startTime = getForecastStartTime();
		String endTime = getForecastEndTime();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			// .addNamedParameter("startFcstTime", QueryParameterValue.string(startTime))
			// .addNamedParameter("endFcstTime", QueryParameterValue.string(endTime))
			.addNamedParameter("nx", QueryParameterValue.string("127"))
			.addNamedParameter("ny", QueryParameterValue.string("60"))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult tableResult = bigQuery.query(queryConfig);

			List<WeatherInfo> weatherInfos = new ArrayList<>();
			for (FieldValueList fieldValues : tableResult.iterateAll()) {
				String forecastTime = fieldValues.get("fcst_date_time").getStringValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime forecastDateTime = LocalDateTime.parse(forecastTime, formatter);

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

	private String getForecastStartTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	private String getForecastEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().plusHours(6L);
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

}
