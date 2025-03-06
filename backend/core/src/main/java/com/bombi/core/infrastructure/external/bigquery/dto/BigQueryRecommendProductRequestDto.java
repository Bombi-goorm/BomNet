package com.bombi.core.infrastructure.external.bigquery.dto;

import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;

public class BigQueryRecommendProductRequestDto {

	private String pnuCode;
	private String sidoCode;
	private String soilTypeCode; // 토양 코드
	private String vldsoildepCode; // 유효 토심 코드
	private String soildraCode; // 배수 등급
	private String acid; // 산도

	public BigQueryRecommendProductRequestDto(
		String pnuCode,
		String sidoCode,
		SoilCharacterResponseDto soilCharacterResponse,
		SoilChemicalResponseDto soilChemicalResponse)
	{
		this.pnuCode = pnuCode;
		this.sidoCode = sidoCode;
		this.soilTypeCode = soilCharacterResponse.getSoilTypeCode();
		this.vldsoildepCode = soilCharacterResponse.getVldsoildepCode();
		this.soildraCode = soilCharacterResponse.getSoildraCode();
		this.acid = soilChemicalResponse.getPH();
	}
}
