package com.bombi.core.presentation.dto.home;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherExpection {

	private String location;
	private List<WeatherInfo> weatherInfo;
}
