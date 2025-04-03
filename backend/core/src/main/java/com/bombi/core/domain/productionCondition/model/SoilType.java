package com.bombi.core.domain.productionCondition.model;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SoilType {

	RICE_NORMAL_FIELD("01","논_보통답"),
	RICE_SANDY_FIELD("02","논_사질답"),
	RICE_IMMATURE_FIELD("03","논_미숙답"),
	RICE_WET_FIELD("04","논_습답"),
	RICE_SALINE_FIELD("05","논_염해답"),
	RICE_ACIDIC_FIELD("06","논_특이산성답"),
	FIELD_NORMAL("07","밭_보통전"),
	FIELD_SANDY("08","밭_사질전"),
	FIELD_IMMATURE("09","밭_미숙전"),
	FIELD_MEDIUM_IMPORTANCE("10","밭_중점전"),
	FIELD_HIGHLAND("11","밭_고원전"),
	FIELD_VOLCANIC_ASH("12","밭_화산회전"),
	FOREST_RED_YELLOW_SOIL("13","임지_적황색토"),
	FOREST_BROWN_SOIL("14","임지_갈색토"),
	FOREST_BROWN_FOREST_SOIL("15","임지_갈색삼림토"),
	FOREST_ROCKY_SOIL("16","임지_암쇄토"),
	FOREST_VOLCANIC_ASH_SOIL("17","임지_화산회토"),
	FOREST_VOLCANIC_ASH_ROCKY_SOIL("18","임지_화산회성암쇄토"),
	FOREST_ACIDIC_BROWN_FOREST_SOIL("19","임지_산성갈색삼림토"),
	FOREST_RED_BROWN_SOIL("20","임지_적갈색토"),
	OTHERS("99","기타");

	private final String code;
	private final String description;

	public static SoilType findByCode(String code) {
		return Arrays.stream(values())
			.filter(value -> value.code.equals(code))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 표토토성 코드입니다."));
	}

}
