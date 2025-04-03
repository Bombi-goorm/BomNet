package com.bombi.core.presentation.dto.home;

import java.util.List;

import com.bombi.core.domain.region.model.Region;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherExpection {

	private String location;
	private List<WeatherInfo> weatherInfo;

	public WeatherExpection(Region region, List<BigqueryForecastResponse> bigqueryForecastResponses) {
		this.location = region.getStationName();
		this.weatherInfo = bigqueryForecastResponses.stream()
			.map(WeatherInfo::new)
			.toList();
	}
}
