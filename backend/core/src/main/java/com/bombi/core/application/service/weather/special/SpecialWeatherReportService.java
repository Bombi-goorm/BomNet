package com.bombi.core.application.service.weather.special;

import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.TimeUtil;
import com.bombi.core.infrastructure.external.weather.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialWeatherReportService {

	private final SpecialWeatherReportApiClient apiClient;

	public SpecialWeatherReportResponse getSpecialWeatherReport() {
		String todayDateTime = TimeUtil.getSpecialWeatherReportStartTime();
		String tomorrowDateTime = TimeUtil.getSpecialWeatherReportEndTime();

		return apiClient.sendSpecialWeatherReport(todayDateTime, tomorrowDateTime);
	}

}
