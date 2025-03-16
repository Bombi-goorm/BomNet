package com.bombi.core.fasttest.weatherforecast;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.infrastructure.external.weather.dto.WeatherForecastResponse;
import com.bombi.core.presentation.dto.home.WeatherExpection;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ForecastController {

	private final ForecastApiClient client;
	private final WeatherForecastApiClient weatherForecastClient;

	// @GetMapping("/weather/forecast")
	// ResponseEntity<?> forecast() {
	// 	ResponseEntity<ForecastApiResponse> responseEntity = client.sendWeatherForecast(55, 127);
	// 	return ResponseEntity.ok(responseEntity.getBody());
	// 	// return ResponseEntity.ok(response);
	// }

	@GetMapping("/weather/forecast")
	ResponseEntity<WeatherForecastResponse> getForecast() {
		WeatherForecastResponse response = weatherForecastClient.sendWeatherForecast("119", "61");
		return ResponseEntity.ok(response);
	}
}
