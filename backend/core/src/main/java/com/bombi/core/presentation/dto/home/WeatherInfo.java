package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherInfo {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime forecastTime; // 예보 시간

	private String temperature; // 기온
	private String humidity; // 습도
	private String windSpeed; // 풍속

	private WeatherStatusResponse weather; // 날씨

	public WeatherInfo(BigqueryForecastResponse bigqueryForecastResponse) {
		this.forecastTime = bigqueryForecastResponse.getForecastTime();
		this.temperature = bigqueryForecastResponse.getTemperature();
		this.humidity = bigqueryForecastResponse.getHumidity();
		this.windSpeed = bigqueryForecastResponse.getWindSpeed();
		this.weather = new WeatherStatusResponse(bigqueryForecastResponse);
	}

	@Builder
	private WeatherInfo(LocalDateTime forecastTime, String temperature, String humidity, String windSpeed,
		WeatherStatusResponse weather) {
		this.forecastTime = forecastTime;
		this.temperature = temperature;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
		this.weather = weather;
	}

}
