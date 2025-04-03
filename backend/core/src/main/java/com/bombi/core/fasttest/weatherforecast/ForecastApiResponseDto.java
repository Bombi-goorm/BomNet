package com.bombi.core.fasttest.weatherforecast;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForecastApiResponseDto {

	private String dateTime;
	private List<ForecastInfoDto> forecastInfos;

	public ForecastApiResponseDto(String stringValue) {
		this.dateTime = stringValue;
		this.forecastInfos = new ArrayList<>();
	}

	public void addForecastInfo(ForecastInfoDto forecastInfoDto) {
		this.forecastInfos.add(forecastInfoDto);
	}
}
