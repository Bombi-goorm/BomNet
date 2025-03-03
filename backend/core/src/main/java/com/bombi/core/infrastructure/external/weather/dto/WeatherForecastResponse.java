package com.bombi.core.infrastructure.external.weather.dto;

import java.util.List;

import com.bombi.core.presentation.dto.home.WeatherInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherForecastResponse {

	private List<WeatherInfo> weatherInfos;

	public WeatherForecastResponse(List<WeatherInfo> weatherInfos) {
		this.weatherInfos = weatherInfos;
	}
}
