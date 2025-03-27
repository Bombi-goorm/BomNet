package com.bombi.core.infrastructure.external.weather.client;

import com.bombi.core.domain.region.model.Region;
import com.bombi.core.presentation.dto.home.WeatherExpection;
import com.bombi.core.presentation.dto.home.BigqueryForecastResponse;
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
	public WeatherExpection sendWeatherForecast(Region region) {

		String query = "SELECT"
			+ " *"
			+ " FROM `goorm-bomnet.kma.int_kma_pivoted_short`"
			+ " WHERE fcst_date_time >= @startFcstTime and fcst_date_time <= @endFcstTime"
			+ " AND nx = @nx AND ny = @ny"
			+ " ORDER BY fcst_date_time ASC, PTY DESC"
			+ " LIMIT 10";

		String startTime = getForecastStartTime();
		String endTime = getForecastEndTime();
		String nx = region.getXx();
		String ny = region.getYy();

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
			.addNamedParameter("startFcstTime", QueryParameterValue.string(startTime))
			.addNamedParameter("endFcstTime", QueryParameterValue.string(endTime))
			.addNamedParameter("nx", QueryParameterValue.string(nx))
			.addNamedParameter("ny", QueryParameterValue.string(ny))
			.setUseLegacySql(false)
			.build();

		System.out.println("==========================");
		try {
			TableResult tableResult = bigQuery.query(queryConfig);
			System.out.println("table");
			System.out.println(tableResult);
			System.out.println(tableResult.toString());
			System.out.println(tableResult.getSchema().getFields().toString());

			List<BigqueryForecastResponse> bigqueryForecastResponses = new ArrayList<>();
			for (FieldValueList fieldValues : tableResult.iterateAll()) {
				String forecastTime = fieldValues.get("fcst_date_time").getStringValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				LocalDateTime forecastDateTime = LocalDateTime.parse(forecastTime, formatter);

				String temperature = fieldValues.get("TMP").getStringValue(); // 온도
				String windSpeed = fieldValues.get("WSD").getStringValue(); // 풍속
				String skyStatus = fieldValues.get("SKY").getStringValue(); // 기상 상태 - 맑음, 조금 흐림, 흐림
				String humidity = fieldValues.get("REH").getStringValue(); // 습도
				String precipitationType = fieldValues.get("PTY").getStringValue(); // 강수 형태

				System.out.println(temperature);
				System.out.println(windSpeed);
				System.out.println(skyStatus);
				System.out.println(humidity);
				System.out.println(precipitationType);

				BigqueryForecastResponse bigqueryForecastResponse = BigqueryForecastResponse.builder()
					.temperature(temperature)
					.forecastTime(forecastDateTime)
					.windSpeed(windSpeed)
					.weather(skyStatus)
					.humidity(humidity)
					.precipitationType(precipitationType)
					.build();
				bigqueryForecastResponses.add(bigqueryForecastResponse);
			}

			return new WeatherExpection(region, bigqueryForecastResponses);
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
