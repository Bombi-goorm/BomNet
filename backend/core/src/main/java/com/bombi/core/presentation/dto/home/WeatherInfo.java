package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;
import java.util.List;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherInfo {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime forecastTime; // 예보 시간

	private String weather; // 날씨
	private String temperature; // 기온
	private String humidity; // 습도
	private String wind; // 풍속
	private String precipitationType; // 강수종류
	private String precipitationMMPerHour; // 강사량
	private String snowPerHour; // 적설량

	public WeatherInfo(LocalDateTime forecastTime, String skyStatus, String temperature, String humidity,
		String windSpeed) {
		this.forecastTime = forecastTime;
		this.weather = skyStatus;
		this.temperature = temperature;
		this.humidity = humidity;
		this.wind = windSpeed;
	}

	@Builder
	public WeatherInfo(LocalDateTime forecastTime, String weather, String temperature, String humidity, String wind,
		String precipitationType, String precipitationMMPerHour, String snowPerHour) {
		this.forecastTime = forecastTime;
		this.weather = weather;
		this.temperature = temperature;
		this.humidity = humidity;
		this.wind = wind;
		this.precipitationType = precipitationType;
		this.precipitationMMPerHour = precipitationMMPerHour;
		this.snowPerHour = snowPerHour;
	}
}
