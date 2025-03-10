package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TopSoilTexture {

	LOAMY_COARSE_SAND("01","양질조사질"),
	LOAMY_FINE_SAND("02","양질세사질"),
	LOAMY_SAND("03","양질사질"),
	FINE_SANDY_LOAM("04","세사양질"),
	SANDY_LOAM("05","사양질"),
	LOAM("06","양질"),
	SILTY_LOAM("07","미사질양질"),
	SILTY_CLAY_LOAM("08","미사질식양질"),
	CLAY_LOAM("09","식양질"),
	OTHERS("99","기타");

	private final String code;
	private final String description;

	public static TopSoilTexture findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 표토토성 코드입니다."));
	}

	public boolean isSuitable(ProductionCondition productionCondition) {
		if (productionCondition == null) {
			return false;
		}

		return this.code.equals(productionCondition.getSoilTexture());
	}
}
