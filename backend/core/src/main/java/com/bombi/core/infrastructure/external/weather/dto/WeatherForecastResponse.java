package com.bombi.core.infrastructure.external.weather.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherForecastResponse {

	private Map<LocalDateTime, List<ForecastInfoDto>> forecastResponseMap;

	public WeatherForecastResponse(Map<LocalDateTime, List<ForecastInfoDto>> forecastResponseMap) {
		this.forecastResponseMap = forecastResponseMap;
	}
}
