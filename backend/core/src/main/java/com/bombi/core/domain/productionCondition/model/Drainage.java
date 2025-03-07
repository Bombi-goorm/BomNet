package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Drainage {

	VERY_GOOD("01","매우 양호"),
	GOOD("02","양호"),
	SLIGHTLY_GOOD("03","약간 양호"),
	SLIGHTLY_BAD("04","약간 불량"),
	BAD("05","불량"),
	VERY_BAD("06","매우 불량");

	private final String code;
	private final String description;

	public static Drainage findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 배수등급 코드입니다."));
	}
}
