package com.bombi.core.application.service;

import org.springframework.stereotype.Component;

import com.bombi.core.domain.product.model.Product;
import com.bombi.core.infrastructure.external.soil.client.SoilCharacterApiClient;
import com.bombi.core.infrastructure.external.soil.client.SoilChemicalApiClient;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.infrastructure.external.weather.client.EnvWeatherInfoApiClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FarmInformationManager {

	private final SoilCharacterApiClient soilCharacterApiClient;
	private final SoilChemicalApiClient soilChemicalApiClient;
	private final EnvWeatherInfoApiClient weatherInfoApiClient;

	/**
	 * 사용자 농장 정보를 모두 가져온다.
	 * 토양 특성정보는 pnuCode를 파라미터로 공공데이터포털api 호출
	 * 평균기온, 평균 강수량은 기상청 api 호출
	 * @param pnuCode
	 * @param product
	 */
	public void analyzeSuitability(String pnuCode, Product product) {
		// 토양 코드, 유효 토심 코드, 배수 등급
		SoilCharacterResponseDto soilCharacterResponse = soilCharacterApiClient.sendSoilCharacter(pnuCode);

		// 산도
		SoilChemicalResponseDto soilChemicalResponse = soilChemicalApiClient.sendSoilChemical(pnuCode);

		//일조량

		//평균기온, 최저기온, 최고기온 -> 기상청 api

		//강수량 -> 기상청 api


	}
}
