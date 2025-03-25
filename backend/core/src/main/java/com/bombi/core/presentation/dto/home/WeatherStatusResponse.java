package com.bombi.core.presentation.dto.home;

import com.bombi.core.infrastructure.external.weather.PrecipitationType;
import com.bombi.core.infrastructure.external.weather.SkyStatus;
import com.bombi.core.infrastructure.external.weather.WeatherStatus;
import com.bombi.core.infrastructure.external.weather.WindStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherStatusResponse {

	private String sky; // 맑음, 구름, 비, 눈
	private String wind; // 바람 유무

	public WeatherStatusResponse(BigqueryForecastResponse bigqueryForecastResponse) {
		String weatherInfoWeather = bigqueryForecastResponse.getWeather(); // 기상 상태
		String temperature = bigqueryForecastResponse.getTemperature();
		String precipitationTypeCode = bigqueryForecastResponse.getPrecipitationType(); // 강수 종류 : 없음, 비, 비/눈, 눈, 소나기
		String windSpeed = bigqueryForecastResponse.getWindSpeed();

		SkyStatus skyStatus = SkyStatus.findByCode(weatherInfoWeather);
		PrecipitationType precipitationType = PrecipitationType.findByCode(precipitationTypeCode);

		this.sky = WeatherStatus.findDescriptionByInfo(skyStatus, precipitationType, temperature);
		this.wind = WindStatus.findStatusByWindSpeed(windSpeed);
	}
}
