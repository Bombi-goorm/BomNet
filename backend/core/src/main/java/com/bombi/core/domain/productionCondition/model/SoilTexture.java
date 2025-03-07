package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SoilTexture {

	LOAMY_COARSE_SAND("01","양질조사토"),
	LOAMY_FINE_SAND("02","양질세사토"),
	LOAMY_SAND("03","양질사토"),
	FINE_SANDY_LOAM("04","세사양토"),
	SANDY_LOAM("05","사양토"),
	LOAM("06","양토"),
	SILTY_LOAM("07","미사질양토"),
	SILTY_CLAY_LOAM("08","미사질식양토"),
	CLAY_LOAM("09","식양토"),
	OTHERS("99","기타");

	private final String code;
	private final String description;

	public static SoilTexture findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 표토토성 코드입니다."));
	}
}
