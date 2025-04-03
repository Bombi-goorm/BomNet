package com.bombi.core.fasttest.weatherforecast;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.weather.forecast.WeatherForecastService;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.presentation.dto.home.WeatherExpection;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ForecastController {

	private final WeatherForecastApiClient client;
	private final RegionRepository regionRepository;
	private final ForecastApiClient forecastApiClient;
	private final WeatherForecastService forecastService;

	@GetMapping("/weather/forecast")
	ResponseEntity<?> forecast() {
		Region region = regionRepository.findByStationName("서울")
			.orElseThrow(() -> new IllegalArgumentException("ForecastController::region find failed."));
		// WeatherExpection weatherExpection = client.sendWeatherForecast(region, forecastStartTime, forecastEndTime);
		WeatherExpection weatherExpection = forecastService.getWeatherForecast(region);

		return ResponseEntity.ok(weatherExpection);

		// return forecastApiClient.sendWeatherForecast(
		// 	Integer.parseInt(region.getXx()), Integer.parseInt(region.getYy()));
	}
}
