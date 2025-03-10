package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SoilSlope {

	FLAT_LAND("01", 0, 2),
	GENTLE_SLOPE("02", 2, 7),
	MODERATE_SLOPE("03", 7, 15),
	STEEP_SLOPE("04", 15, 30), // 100cm 이상
	VERY_STEEP_SLOPE("04", 30, 60), // 100cm 이상
	EXTREME_SLOPE("04", 60, 100), // 100cm 이상
	OTHER("99", -1, -1); // 기타의 경우 범위를 지정할 필요 없음

	private final String code;
	private final int minSlope;
	private final int maxSlope;

	public static SoilSlope findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경사도 코드입니다."));
	}

	public boolean isSuitable(ProductionCondition productionCondition) {
		int soilSlopeDegree = (int)(Double.parseDouble(productionCondition.getSlopeDegree()));
		return (soilSlopeDegree >= this.minSlope && soilSlopeDegree <= this.maxSlope);
	}

	public String getDescription() {
		return this.minSlope + "-" + this.maxSlope;
	}

}
