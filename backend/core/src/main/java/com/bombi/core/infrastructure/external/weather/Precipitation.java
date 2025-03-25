package com.bombi.core.infrastructure.external.weather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Precipitation {

	LIGHT("1", 0, 3,"약한 비", "F"),
	MODERATE("2", 3, 15, "보통 비", "T"),
	HEAVY("3", 15, Integer.MAX_VALUE, "강한 비", "T");

	private final String code;
	private final int minimum;
	private final int maximum;
	private final String description;
	private final String expectionDescription;




}
