package com.bombi.core.presentation.dto.member;

import com.bombi.core.domain.region.model.RegionWeather;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmInfoResponseDto {

	private String soilType;
	private String averageTemperature;
	private String annualPrecipitation;

	public FarmInfoResponseDto(RegionWeather regionWeather) {
		this.soilType = null;
		this.averageTemperature = regionWeather.getAverageTemperature();
		this.annualPrecipitation = regionWeather.getAnnualPrecipitation();
	}
}
