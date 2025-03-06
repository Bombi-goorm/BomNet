package com.bombi.core.infrastructure.external.soil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoilCharacterResponseDto {

	private String soilTypeCode; // 토양 코드
	private String vldsoildepCode; // 유효 토심 코드
	private String soildraCode; // 배수 등급
	private String surttureCode; // 표토 토성 코드

	public SoilCharacterResponseDto(String soilTypeCode, String vldsoildepCode, String soildraCode, String surttureCode) {
		this.soilTypeCode = soilTypeCode;
		this.vldsoildepCode = vldsoildepCode;
		this.soildraCode = soildraCode;
		this.surttureCode = surttureCode;
	}
}
