package com.bombi.core.application.service.weather.forecast;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.time.TimePolicy;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.presentation.dto.home.WeatherExpection;

@Service
public class WeatherForecastService {

	private final TimePolicy timePolicy;
	private final WeatherForecastApiClient apiClient;

	public WeatherForecastService(@Qualifier("forecastTimePolicy") TimePolicy timePolicy,
		WeatherForecastApiClient apiClient) {
		this.timePolicy = timePolicy;
		this.apiClient = apiClient;
	}

	public WeatherExpection getWeatherForecast(Region region) {
		String forecastStartTime = timePolicy.getStartTime();
		String forecastEndTime = timePolicy.getEndTime();

		return apiClient.sendWeatherForecast(region, forecastStartTime, forecastEndTime);
	}
}
