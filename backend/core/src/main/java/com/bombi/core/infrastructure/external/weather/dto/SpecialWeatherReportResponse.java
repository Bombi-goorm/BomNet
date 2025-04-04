package com.bombi.core.infrastructure.external.weather.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialWeatherReportResponse {
	private List<SpecialWeatherReport> item;

	public SpecialWeatherReportResponse(List<SpecialWeatherReport> reports) {
		this.item = reports;
	}
}
