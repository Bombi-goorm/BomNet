package com.bombi.core.infrastructure.external.weather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Precipitation {

	CLEAR("1", 0, 3,"약한 비"),
	CLOUDY("2", 3, 15, "보통 비"),
	FADE("3", 15, Integer.MAX_VALUE, "강한 비");

	private final String code;
	private final int minimum;
	private final int maximum;
	private final String description;

}
