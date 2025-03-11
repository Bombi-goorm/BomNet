package com.bombi.core.infrastructure.external.soil.dto;

import com.bombi.core.domain.productionCondition.model.DeepSoilTexture;
import com.bombi.core.domain.productionCondition.model.SoilSlope;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoilSectionResponseDto {

	private DeepSoilTexture deepSoilTexture;
	private SoilSlope soilSlope;

	public SoilSectionResponseDto(String deepsoilQltCode, String soilslopeCode) {
		this.deepSoilTexture = DeepSoilTexture.findByCode(deepsoilQltCode);
		this.soilSlope = SoilSlope.findByCode(soilslopeCode);
	}
}
