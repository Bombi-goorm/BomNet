package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeepSoilTexture {

	SANDY("01", "사질"),
	SANDY_LOAM("02", "사양질"),
	SILT_SANDY_LOAM("03", "미사사양질"),
	CLAY_LOAM("04", "식양질"),
	SILTY_CLAY_LOAM("05", "미사식양질"),
	CLAY("06", "식질"),
	OTHER("99", "기타");

	private final String code;
	private final String description;

	public static DeepSoilTexture findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 심토토성 코드입니다."));
	}

	// public boolean isSuitable(ProductionCondition productionCondition) {
	// 	return this.description.equals(productionCondition.getDrainage());
	// }
}
