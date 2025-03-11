package com.bombi.core.presentation.dto.member;

import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmInfoResponseDto {

	private String averageTemperature; // 평균기온
	private String minTemperature; // 최저기온
	private String maxTemperature; // 최고기온
	private String annualRainfall; // 연간 강수량
	private String sunlightHours; // 연간 일조량
	private String drainage; // 배수등급
	private String soilDepth; // 토심
	private String slopeDegree; // 경사도
	private String soilTexture; // 토성
	private String ph; // pH
	private String organicMatterGPerKg; // 유기물
	private String avPMgPerKg; // 유효인산
	private String kMgPerKg; // 칼륨
	private String caMgPerKg; // 칼슘
	private String mgMgPerKg; // 마그네슘

	public FarmInfoResponseDto(
		RegionWeather regionWeather,
		SoilCharacterResponseDto characterResponseDto,
		SoilChemicalResponseDto chemicalResponseDto
	) {
		this.averageTemperature = String.valueOf(regionWeather.getAverageTemperature());
		this.minTemperature = String.valueOf(regionWeather.getMinTemperature());
		this.maxTemperature = String.valueOf(regionWeather.getMaxTemperature());
		this.annualRainfall = String.valueOf(regionWeather.getAnnualPrecipitation());
		this.sunlightHours = String.valueOf(regionWeather.getAnnualSunlightHours());
		this.drainage = characterResponseDto.getSoildraCode().getDescription();
		this.soilDepth = characterResponseDto.getVldsoildepCode().getDescription();
		this.slopeDegree = null;
		this.soilTexture = characterResponseDto.getSurttureCode().getDescription();
		this.ph = chemicalResponseDto.getPH();
		this.organicMatterGPerKg = chemicalResponseDto.getOrganicMatterGPerKg();
		this.avPMgPerKg = chemicalResponseDto.getAvailablePhosphorus();
		this.kMgPerKg = chemicalResponseDto.getKCMolPerKg();
		this.caMgPerKg = chemicalResponseDto.getCaCMolPerKg();
		this.mgMgPerKg = chemicalResponseDto.getMgCMolPerKg();
	}
}
