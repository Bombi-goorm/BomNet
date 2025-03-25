package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BigqueryForecastResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime forecastTime; // 예보 시간

	private String weather; // 날씨
	private String temperature; // 기온
	private String humidity; // 습도
	private String windSpeed; // 풍속
	private String precipitationType; // 강수종류

	public BigqueryForecastResponse(LocalDateTime forecastTime, String skyStatus, String temperature, String humidity,
		String windSpeed) {
		this.forecastTime = forecastTime;
		this.weather = skyStatus;
		this.temperature = temperature;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
	}

	@Builder
	public BigqueryForecastResponse(LocalDateTime forecastTime, String weather, String temperature, String humidity, String windSpeed,
		String precipitationType) {
		this.forecastTime = forecastTime;
		this.weather = weather;
		this.temperature = temperature;
		this.humidity = humidity;
		this.windSpeed = windSpeed;
		this.precipitationType = precipitationType;
	}
}
