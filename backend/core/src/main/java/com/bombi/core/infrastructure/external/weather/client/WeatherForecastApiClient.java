package com.bombi.core.infrastructure.external.weather.client;

import com.bombi.core.infrastructure.external.weather.dto.WeatherForecastResponse;
import com.bombi.core.presentation.dto.home.WeatherInfo;
import com.google.cloud.bigquery.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherForecastApiClient {

	private final BigQuery bigQuery;

	/**
	 * 단기 예보 조회
	 * @return
	 */
	public WeatherForecastResponse sendWeatherForecast(String nx, String ny) {

		String query = "SELECT"
			+ " *"
			+ " FROM `goorm-bomnet.kma.int_kma_pivoted_short`"
			+ " WHERE fcst_date_time >= @startFcstTime and fcst_date_time <= @endFcstTime"
			+ " AND nx = @nx AND ny = @ny"
			+ " ORDER BY fcst_date_time"
			+ " LIMIT 10";

		String startTime = getForecastStartTime();
		String endTime = getForecastEndTime();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("startFcstTime", QueryParameterValue.string(startTime))
			.addNamedParameter("endFcstTime", QueryParameterValue.string(endTime))
			.addNamedParameter("nx", QueryParameterValue.string(nx))
			.addNamedParameter("ny", QueryParameterValue.string(ny))
			.setUseLegacySql(false)
			.build();

		try {
			TableResult tableResult = bigQuery.query(queryConfig);

			for (FieldValueList value : tableResult.getValues()) {
				System.out.print("x = " + value.get("nx"));
				System.out.println(" y = " + value.get("ny"));
			}

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
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(3L);
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

	private String getForecastEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(3L).plusHours(6L);
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	}

}
