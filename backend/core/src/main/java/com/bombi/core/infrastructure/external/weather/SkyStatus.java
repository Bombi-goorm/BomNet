package com.bombi.core.infrastructure.external.weather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkyStatus {

	CLEAR("1", "clear"),
	CLOUDY("3", "cloudy"),
	FADE("4", "fade");

	private final String code;
	private final String description;

}
