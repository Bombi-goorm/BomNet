package com.bombi.core.application.service.weather.forecast;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.presentation.dto.home.WeatherExpection;

@Service
public class WeatherForecastService {

	private final TimeStringProvider timeStringProvider;
	private final WeatherForecastApiClient apiClient;

	public WeatherForecastService(@Qualifier("forecastTimeStringProvider") TimeStringProvider timeStringProvider,
		WeatherForecastApiClient apiClient) {
		this.timeStringProvider = timeStringProvider;
		this.apiClient = apiClient;
	}

	public WeatherExpection getWeatherForecast(Region region) {
		String forecastStartTime = timeStringProvider.getStartDateString();
		String forecastEndTime = timeStringProvider.getEndDateString();

		return apiClient.sendWeatherForecast(region, forecastStartTime, forecastEndTime);
	}
}
