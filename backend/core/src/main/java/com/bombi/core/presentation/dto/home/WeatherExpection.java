package com.bombi.core.presentation.dto.home;

import java.util.List;

import com.bombi.core.infrastructure.external.weather.dto.WeatherForecastResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherExpection {

	private String location;
	private List<WeatherInfo> weatherInfo;

	public WeatherExpection(WeatherForecastResponse weatherForecastResponse) {
		this.location = "제주";
		this.weatherInfo = weatherForecastResponse.getWeatherInfos();
	}
}
