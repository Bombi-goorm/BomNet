package com.bombi.core.application.service.weather.special;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.time.TimePolicy;
import com.bombi.core.infrastructure.external.weather.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;

@Service
public class SpecialWeatherReportService {

	private final TimePolicy timePolicy;
	private final SpecialWeatherReportApiClient apiClient;

	public SpecialWeatherReportService(@Qualifier("specialReportTimePolicy") TimePolicy timePolicy,
		SpecialWeatherReportApiClient apiClient) {
		this.timePolicy = timePolicy;
		this.apiClient = apiClient;
	}

	public SpecialWeatherReportResponse getSpecialWeatherReport() {
		String startTime = timePolicy.getStartTime();
		String endTime = timePolicy.getEndTime();

		return apiClient.sendSpecialWeatherReport(startTime, endTime);
	}

}
