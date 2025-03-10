package com.bombi.core.presentation.dto.member;

import com.bombi.core.domain.region.model.RegionWeather;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FarmInfoResponseDto {

	private String overwintering; // -> 월동여부는 농지정보와는 무관
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

}
