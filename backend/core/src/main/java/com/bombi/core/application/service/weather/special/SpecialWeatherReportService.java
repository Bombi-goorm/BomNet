package com.bombi.core.application.service.weather.special;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.weather.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialWeatherReportService {

	private final SpecialWeatherReportApiClient apiClient;

	public SpecialWeatherReportResponse getSpecialWeatherReport() {
		String todayDateTime = getTodayDateTime();
		String tomorrowDateTime = getTomorrowDateTime();

		return apiClient.sendSpecialWeatherReport(todayDateTime, tomorrowDateTime);
	}

	private String getTodayDateTime() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(today, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	private String getTomorrowDateTime() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0);
		LocalDateTime localDateTime = LocalDateTime.of(tomorrow, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
