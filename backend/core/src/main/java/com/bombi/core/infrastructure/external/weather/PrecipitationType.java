package com.bombi.core.infrastructure.external.weather;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrecipitationType {

	NONE("0", "없음"),
	RAIN("1", "비"),
	RAIN_AND_SNOW("2", "비/눈"),
	SNOW("3", "눈"),
	SHOWER("4", "소나기");


	private final String code;
	private final String description;

	public static PrecipitationType findByCode(String code) {
		return Arrays.stream(values())
			.filter(type -> type.getCode().equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당하는 강수 타입 코드가 없습니다."));
	}
}
