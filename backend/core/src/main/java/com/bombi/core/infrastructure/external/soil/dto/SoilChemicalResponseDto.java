package com.bombi.core.infrastructure.external.soil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoilChemicalResponseDto {

	private String acid; // 산도

	public SoilChemicalResponseDto(String acid) {
		this.acid = acid;
	}
}
