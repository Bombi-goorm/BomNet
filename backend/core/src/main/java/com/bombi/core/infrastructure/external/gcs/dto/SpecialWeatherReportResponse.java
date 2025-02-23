package com.bombi.core.infrastructure.external.gcs.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialWeatherReportResponse {
	private List<SpecialWeatherReport> item;
}
