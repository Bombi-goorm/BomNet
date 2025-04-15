package com.bombi.core.application.service.weather.forecast;

import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.TimeUtil;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.presentation.dto.home.WeatherExpection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherForecastService {

	private final WeatherForecastApiClient apiClient;

	public WeatherExpection getWeatherForecast(Region region) {
		String forecastStartTime = TimeUtil.getForecastStartTime();
		String forecastEndTime = TimeUtil.getForecastEndTime();

		return apiClient.sendWeatherForecast(region, forecastStartTime, forecastEndTime);
	}
}
