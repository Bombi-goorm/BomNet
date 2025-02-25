package com.bombi.core.fasttest.weatherforecast;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

	private final WeatherForecastApiClient client;

	@GetMapping("/weather/forecast")
	ResponseEntity<?> forecast() {
		WeatherForecastResponse weatherForecastResponse = client.sendWeatherForecast();

		Map<LocalDateTime, List<ForecastInfoDto>> response = weatherForecastResponse.getForecastResponseMap();
		WeatherExpection weatherExpection = new WeatherExpection(response);

		return ResponseEntity.ok(weatherExpection);
	}
}
