package com.bombi.core.infrastructure.external.weather.dto;

import java.util.List;

import com.bombi.core.presentation.dto.home.BigqueryForecastResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherForecastResponse {

	private List<BigqueryForecastResponse> bigqueryForecastResponses;

	public WeatherForecastResponse(List<BigqueryForecastResponse> bigqueryForecastResponses) {
		this.bigqueryForecastResponses = bigqueryForecastResponses;
	}
}
