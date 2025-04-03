package com.bombi.core.presentation.dto.weather;

import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReport;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WeatherNoticeResponseDto {

	private String dateTime;
	private String location;
	private String content;

	public WeatherNoticeResponseDto(SpecialWeatherReport item) {
		this.dateTime = String.valueOf(item.getTmFc());
		this.location = item.getStnId();
		this.content = item.getTitle();
	}
}
