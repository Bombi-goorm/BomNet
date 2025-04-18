package com.bombi.core.infrastructure.external.weather.client;

import com.bombi.core.common.annotation.BigQueryData;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.presentation.dto.home.WeatherExpection;
import com.bombi.core.presentation.dto.home.BigqueryForecastResponse;
import com.google.cloud.bigquery.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherForecastApiClient {

	private final BigQuery bigQuery;

	/**
	 * 단기 예보 조회
	 * @return
	 */
	@BigQueryData
	@Cacheable(key = "#region.id", value = "Forecast")
	public WeatherExpection sendWeatherForecast(Region region, String startTime, String endTime) {

		String query = "SELECT * FROM `goorm-bomnet.kma.int_kma_pivoted_short`"
				+ " WHERE fcst_date_time >= @startFcstTime and fcst_date_time <= @endFcstTime"
				+ " AND nx = @nx AND ny = @ny"
				+ " ORDER BY fcst_date_time ASC";

//		+ " WHERE fcst_date_time >= '2025-03-27 00:00:00' and fcst_date_time <= '2025-03-28 00:00:00'"
//				+ " WHERE nx = '60' AND ny = '127'"

		// String startTime = getForecastStartTime();
		// String endTime = getForecastEndTime();
		String nx = region.getXx();
		String ny = region.getYy();

		// log.info("Start time: {}, End time : {}", startTime, endTime);
		// log.info("NX: {}, NY: {}", nx, ny);

		QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query)
				.addNamedParameter("startFcstTime", QueryParameterValue.string(startTime))
				.addNamedParameter("endFcstTime", QueryParameterValue.string(endTime))
				.addNamedParameter("nx", QueryParameterValue.string(nx))
				.addNamedParameter("ny", QueryParameterValue.string(ny))
				.setUseLegacySql(false)
				.build();


		try {
			TableResult tableResult = bigQuery.query(queryConfig);

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
// 		LocalDate localDate = LocalDate.now();
// 		LocalTime midnight = LocalTime.MIDNIGHT;
// 		LocalDateTime localDateTime = LocalDateTime.of(localDate, midnight);
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private String getForecastEndTime() {
    LocalDateTime localDateTime = LocalDateTime.now().plusHours(6L);
// 		LocalDate localDate = LocalDate.now().plusDays(1);
// 		LocalTime midnight = LocalTime.MIDNIGHT;
// 		LocalDateTime localDateTime = LocalDateTime.of(localDate, midnight);
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}
