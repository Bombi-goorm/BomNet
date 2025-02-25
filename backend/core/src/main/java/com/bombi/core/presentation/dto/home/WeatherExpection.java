package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.parameters.P;

import com.bombi.core.fasttest.weatherforecast.ForecastInfoDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherExpection {

	private String location;
	private List<WeatherInfo> weatherInfo;

	public WeatherExpection(Map<LocalDateTime, List<ForecastInfoDto>> forecastInfoMap) {
		this.location = "제주";
		this.weatherInfo = forecastInfoMap.entrySet().stream()
			.map(entry -> {
				LocalDateTime forecastTime = entry.getKey();
				List<ForecastInfoDto> forecastInfo = entry.getValue();

				return new WeatherInfo(forecastTime, forecastInfo);
			})
			.toList();
	}
}
