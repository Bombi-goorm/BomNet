package com.bombi.core.presentation.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmInfoResponseDto {

	private String sido;
	private String destrict;
	private String sidoCode;
	private String destrictCode;
	private String soilType;
	private String averageTemperature;
	private String annualPrecipitation;
}
