package com.bombi.core.presentation.dto.product;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmSuitability {
	// private List<AnalyzeResponse> analyze;

	private SuitabilityResult soilPhysicalSuitability;  // 토양 물리적 특성
	private SuitabilityResult soilChemicalSuitability;  // 토양 화학적 특성
	private SuitabilityResult climateSuitability;       // 기후 적합도

	public FarmSuitability(SuitabilityResult physicalSuitability, SuitabilityResult chemicalSuitability,
		SuitabilityResult climateSuitability) {
		this.soilPhysicalSuitability = physicalSuitability;
		this.soilChemicalSuitability = chemicalSuitability;
		this.climateSuitability = climateSuitability;
	}
}
