package com.bombi.core.infrastructure.external.soil.dto;

import com.bombi.core.domain.productionCondition.model.SoilDrainage;
import com.bombi.core.domain.productionCondition.model.SoilDepth;
import com.bombi.core.domain.productionCondition.model.TopSoilTexture;
import com.bombi.core.domain.productionCondition.model.SoilType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoilCharacterResponseDto {

	private SoilType soilTypeCode; // 토양 코드
	private SoilDepth vldsoildepCode; // 유효 토심 코드
	private SoilDrainage soildraCode; // 배수 등급
	private TopSoilTexture surttureCode; // 표토 토성 코드

	public SoilCharacterResponseDto(String soilTypeCode, String vldsoildepCode, String soildraCode, String surttureCode) {
		this.soilTypeCode = SoilType.findByCode(soilTypeCode);
		this.vldsoildepCode = SoilDepth.findByCode(vldsoildepCode);
		this.soildraCode = SoilDrainage.findByCode(soildraCode);
		this.surttureCode = TopSoilTexture.findByCode(surttureCode);
	}
}
