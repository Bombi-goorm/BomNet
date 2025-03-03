package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;
import java.util.List;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	public WeatherInfo(LocalDateTime forecastTime, String skyStatus, String temperature, String humidity,
		String windSpeed) {
		this.forecastTime = forecastTime;
		this.weather = skyStatus;
		this.temperature = temperature;
		this.humidity = humidity;
		this.wind = windSpeed;
	}
}
