package com.bombi.core.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bombi.core.domain.product.model.Product;
import com.bombi.core.domain.productionCondition.model.ProductionCondition;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.soil.client.SoilCharacterApiClient;
import com.bombi.core.infrastructure.external.soil.client.SoilChemicalApiClient;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.presentation.dto.product.FarmSuitability;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FarmAnalyzer {

	private final SoilCharacterApiClient soilCharacterApiClient;
	private final SoilChemicalApiClient soilChemicalApiClient;

	private final RegionRepository regionRepository;

	/**
	 * 사용자 농장 정보를 모두 가져온다.
	 * 토양 특성정보는 pnuCode를 파라미터로 공공데이터포털api 호출
	 *
	 * @param pnuCode
	 * @param product
	 * @return
	 */
	public FarmSuitability analyzeSuitability(String pnuCode, Product product) {
		// 토양 유형, 유효 토심 코드, 배수 등급, 토성
		SoilCharacterResponseDto soilCharacterResponse = soilCharacterApiClient.sendSoilCharacter(pnuCode);

		// ph, 유기물, 유효인산, 칼륨, 칼슘, 마그네슘
		SoilChemicalResponseDto soilChemicalResponse = soilChemicalApiClient.sendSoilChemical(pnuCode);

		// 내 농지의 기온정보 가져오기
		String weatherSiDoCode = pnuCode.substring(0, 2);
		Region region = regionRepository.findByWeatherSiDoCode(weatherSiDoCode)
			.orElseThrow(() -> new IllegalArgumentException("지역 정보를 찾을 수 없습니다."));
		RegionWeather regionWeather = region.getRegionWeather();

		// 작물 생산 조건과 외부 데이터 비교하기
		ProductionCondition productionCondition = product.getCultivation().getProductionCondition();

		//물리 조건 비교 : 토성, 유효토심, 배수등급
		Map<String, Boolean> physicalSuitability = analyzeSoilPhysicalSuitability(productionCondition,
			soilCharacterResponse);

		//화학 조건 비교 : pH, 유기물, 유효인산, 칼륨, 칼슘, 마그네슘
		Map<String, Boolean> chemicalSuitability = analyzeSoilChemicalSuitability(productionCondition,
			soilChemicalResponse);

		// 기후 조건 비교
		Map<String, Boolean> weatherSuitability = analyzeWeatherSuitability(productionCondition, regionWeather);

		return null;
	}

	private Map<String, Boolean> analyzeWeatherSuitability(ProductionCondition productionCondition, RegionWeather regionWeather) {
		Map<String, Boolean> weatherSuitabilityMap = new HashMap<>();

		boolean temperatureSuitability = productionCondition.isTemperatureSuitable(regionWeather.getAverageTemperature(),
			regionWeather.getMaxTemperature(), regionWeather.getMinTemperature());
		boolean precipitationSuitability = productionCondition.isRainfallSuitable(regionWeather.getAnnualPrecipitation());
		boolean sunlightSuitability = productionCondition.isSunlightSuitable(regionWeather.getAnnualSunlightHours());

		weatherSuitabilityMap.put("temperature", temperatureSuitability); // 기온
		weatherSuitabilityMap.put("precipitation", precipitationSuitability); // 강수량
		weatherSuitabilityMap.put("sunlightHours", sunlightSuitability); // 일조량

		// List<String> unsuitableProperties = weatherSuitabilityMap.entrySet().stream()
		// 	.filter(entry -> !entry.getValue())
		// 	.map(Map.Entry::getKey)
		// 	.toList();

		return weatherSuitabilityMap;
	}

	private Map<String, Boolean> analyzeSoilChemicalSuitability(ProductionCondition productionCondition,
		SoilChemicalResponseDto soilChemicalResponse) {

		Map<String, Boolean> chemicalSuitabilityMap = new HashMap<>();

		boolean pHSuitability = productionCondition.isPHSuitable(soilChemicalResponse.getPH());
		boolean organicMatterSuitability = productionCondition.isOrganicMatterSuitable(soilChemicalResponse.getOrganicMatterGPerKg());
		boolean phosphorusSuitability = productionCondition.isPhosphorusSuitable(soilChemicalResponse.getAvailablePhosphorus());
		boolean potassiumSuitability = productionCondition.isPotassiumSuitable(soilChemicalResponse.getKCMolPerKg());
		boolean calciumSuitability = productionCondition.isCalciumSuitable(soilChemicalResponse.getCaCMolPerKg());
		boolean magnesiumSuitability = productionCondition.isMagnesiumSuitable(soilChemicalResponse.getMgCMolPerKg());

		chemicalSuitabilityMap.put("pH", pHSuitability);
		chemicalSuitabilityMap.put("OrganicMatter", organicMatterSuitability);
		chemicalSuitabilityMap.put("phosphorus", phosphorusSuitability);
		chemicalSuitabilityMap.put("potassium", potassiumSuitability);
		chemicalSuitabilityMap.put("calcium", calciumSuitability);
		chemicalSuitabilityMap.put("magnesium", magnesiumSuitability);

		return chemicalSuitabilityMap;
	}

	private Map<String, Boolean> analyzeSoilPhysicalSuitability(ProductionCondition productionCondition,
		SoilCharacterResponseDto soilCharacterResponse) {
		Map<String, Boolean> physicalSuitabilityMap = new HashMap<>();

		boolean soilTextureSuitability = productionCondition.isSoilTextureSuitable(soilCharacterResponse.getSurttureCode());
		boolean soilDepthSuitability = productionCondition.isSoilDepthSuitable(soilCharacterResponse.getVldsoildepCode());
		boolean drainageSuitableSuitability = productionCondition.isDrainageSuitable(soilCharacterResponse.getSoildraCode());

		physicalSuitabilityMap.put("soilTexture", soilTextureSuitability);
		physicalSuitabilityMap.put("soilDepth", soilDepthSuitability);
		physicalSuitabilityMap.put("drainage", drainageSuitableSuitability);

		return physicalSuitabilityMap;
	}

}
