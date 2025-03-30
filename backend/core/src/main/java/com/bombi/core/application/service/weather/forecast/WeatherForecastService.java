package com.bombi.core.application.service.weather.forecast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.bombi.core.domain.region.model.Region;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.presentation.dto.home.WeatherExpection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherForecastService {

	private final WeatherForecastApiClient apiClient;

	public WeatherExpection getWeatherForecast(Region region) {
		String forecastStartTime = getForecastStartTime();
		String forecastEndTime = getForecastEndTime();

		return apiClient.sendWeatherForecast(region, forecastStartTime, forecastEndTime);
	}

	private String getForecastStartTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private String getForecastEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().plusHours(6L);
		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
