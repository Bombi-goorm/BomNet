package com.bombi.core.application.service.weather.special;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.weather.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;

@Service
public class SpecialWeatherReportService {

	private final TimeStringProvider timeStringProvider;
	private final SpecialWeatherReportApiClient apiClient;

	public SpecialWeatherReportService(@Qualifier("specialWeatherTimeStringProvider") TimeStringProvider timeStringProvider,
		SpecialWeatherReportApiClient apiClient) {
		this.timeStringProvider = timeStringProvider;
		this.apiClient = apiClient;
	}

	public SpecialWeatherReportResponse getSpecialWeatherReport() {
		String startTime = timeStringProvider.getStartDateString();
		String endTime = timeStringProvider.getEndDateString();

		return apiClient.sendSpecialWeatherReport(startTime, endTime);
	}

}
