package com.bombi.core.presentation.dto.home;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherInfo {

	private String weather;
	private String temperature;
	private String humidity;
	private String wind;
	private String dateTime;
}
