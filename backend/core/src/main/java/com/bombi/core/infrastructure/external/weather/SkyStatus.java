package com.bombi.core.infrastructure.external.weather;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkyStatus {

	CLEAR("1", "맑음"),
	CLOUDY("3", "구름 많음"),
	FADE("4", "흐림");

	private final String code;
	private final String description;

	public static SkyStatus findByCode(String code) {
		return Arrays.stream(values()).filter(skyStatus -> skyStatus.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당하는 SKY 코드가 없습니다."));
	}
}
