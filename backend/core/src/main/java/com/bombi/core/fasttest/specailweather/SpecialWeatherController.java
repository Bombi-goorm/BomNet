package com.bombi.core.fasttest.specailweather;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.weather.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SpecialWeatherController {
	private final SpecialWeatherReportApiClient specialWeatherReportApiClient;
	private final RegionRepository regionRepository;

	@GetMapping("/weather/special")
	ResponseEntity<?> specialWeather() {
		SpecialWeatherReportResponse response = specialWeatherReportApiClient.sendSpecialWeatherReport();

		return ResponseEntity.ok(response);
	}


}
