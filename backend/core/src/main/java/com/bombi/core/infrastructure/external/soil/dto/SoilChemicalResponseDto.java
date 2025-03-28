package com.bombi.core.infrastructure.external.soil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoilChemicalResponseDto {

	private String ph; // 산도
	private String availablePhosphorus; // 유효인산
	private String organicMatterGPerKg;
	private String kaCMolPerKg;
	private String caCMolPerKg;
	private String mgCMolPerKg;

	public SoilChemicalResponseDto(
		String ph,
		String availablePhosphorus,
		String organicMatterGPerKg,
		String kaCMolPerKg,
		String caCMolPerKg,
		String mgCMolPerKg)
	{
		this.ph = ph;
		this.availablePhosphorus = availablePhosphorus;
		this.organicMatterGPerKg = organicMatterGPerKg;
		this.kaCMolPerKg = kaCMolPerKg;
		this.caCMolPerKg = caCMolPerKg;
		this.mgCMolPerKg = mgCMolPerKg;
	}
}
