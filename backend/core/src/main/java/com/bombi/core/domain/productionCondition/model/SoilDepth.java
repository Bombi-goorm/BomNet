package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SoilDepth {

	VERY_SHALLOW("01", 0, 25),
	SHALLOW("02", 25, 50),
	NORMAL("03", 50, 100),
	DEEP("04", 100, Integer.MAX_VALUE), // 100cm 이상
	OTHER("99", -1, -1); // 기타의 경우 범위를 지정할 필요 없음

	private final String code;
	private final int minDepth;
	private final int maxDepth;

	public static SoilDepth findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유효토심코드입니다."));
	}

}
