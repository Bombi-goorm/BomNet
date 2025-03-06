package com.bombi.core.infrastructure.external.soil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SoilChemicalResponseDto {

	private String pH; // 산도
	private String availablePhosphorus; // 유효인산
	private String organicMatterGPerKg;
	private String KCMolPerKg;
	private String CaCMolPerKg;
	private String MgCMolPerKg;

	public SoilChemicalResponseDto(
		String pH,
		String availablePhosphorus,
		String organicMatterGPerKg,
		String KCMolPerKg,
		String caCMolPerKg,
		String mgCMolPerKg)
	{
		this.pH = pH;
		this.availablePhosphorus = availablePhosphorus;
		this.organicMatterGPerKg = organicMatterGPerKg;
		this.KCMolPerKg = KCMolPerKg;
		CaCMolPerKg = caCMolPerKg;
		MgCMolPerKg = mgCMolPerKg;
	}
}
