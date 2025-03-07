package com.bombi.core.application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.bombi.core.domain.product.model.Product;
import com.bombi.core.domain.productionCondition.model.SoilDrainage;
import com.bombi.core.domain.productionCondition.model.ProductionCondition;
import com.bombi.core.domain.productionCondition.model.SoilDepth;
import com.bombi.core.domain.productionCondition.model.SoilTexture;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.soil.client.SoilCharacterApiClient;
import com.bombi.core.infrastructure.external.soil.client.SoilChemicalApiClient;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.presentation.dto.product.FarmSuitability;
import com.bombi.core.presentation.dto.product.SuitabilityResult;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FarmAnalyzer {

	private final SoilCharacterApiClient soilCharacterApiClient;
	private final SoilChemicalApiClient soilChemicalApiClient;

	private final RegionRepository regionRepository;

	/**
	 * pnu코드를 기반으로 농지의 화학적특성, 물리적 특성, 기온 정보를 가져온다.
	 * 화학적특성, 물리적특성은 공공데이터 포털 api 호출
	 * 기후조건은 db의 데이터로 비교
	 * @param pnuCode : 필지번호
	 * @param product : 선택한 작물
	 * @return 분석 정보
	 */
	public FarmSuitability analyzeSuitability(String pnuCode, Product product) {
		// 내 농지의 기온정보 가져오기
		String weatherSiDoCode = pnuCode.substring(0, 5);
		Region region = regionRepository.findByWeatherSiGunGuCode(weatherSiDoCode)
			.orElseThrow(() -> new IllegalArgumentException("지역 정보를 찾을 수 없습니다."));
		RegionWeather regionWeather = region.getRegionWeather();

		// 작물 생산 조건과 외부 데이터 비교하기
		ProductionCondition productionCondition = product.getCultivation().getProductionCondition();

		//물리 조건 분석 : 토성, 유효토심, 배수등급
		SoilCharacterResponseDto soilCharacterResponse = soilCharacterApiClient.sendSoilCharacter(pnuCode);
		SuitabilityResult physicalSuitability = analyzeSoilPhysicalSuitability(productionCondition,
			soilCharacterResponse);

		//화학 조건 분석 : pH, 유기물, 유효인산, 칼륨, 칼슘, 마그네슘
		SoilChemicalResponseDto soilChemicalResponse = soilChemicalApiClient.sendSoilChemical(pnuCode);
		SuitabilityResult chemicalSuitability = analyzeSoilChemicalSuitability(productionCondition,
			soilChemicalResponse);

		// 기후 조건 분석
		SuitabilityResult climateSuitability = analyzeWeatherSuitability(productionCondition, regionWeather);

		return new FarmSuitability(physicalSuitability, chemicalSuitability, climateSuitability);
	}

	private SuitabilityResult analyzeSoilPhysicalSuitability(ProductionCondition productionCondition,
		SoilCharacterResponseDto soilCharacterResponse) {
		if(soilCharacterResponse == null) {
			return null;
		}

		Map<String, Boolean> physicalSuitabilityMap = new HashMap<>();

		//토성
		SoilTexture soilTexture = soilCharacterResponse.getSurttureCode();
		boolean soilTextureSuitability = soilTexture.isSuitable(productionCondition);

		//유효 토심
		SoilDepth soilDepth = soilCharacterResponse.getVldsoildepCode();
		boolean soilDepthSuitability = soilDepth.isSuitable(productionCondition);

		//배수 등급
		SoilDrainage soilDrainage = soilCharacterResponse.getSoildraCode();
		boolean drainageSuitableSuitability = soilDrainage.isSuitable(productionCondition);

		physicalSuitabilityMap.put("soilTexture", soilTextureSuitability);
		physicalSuitabilityMap.put("soilDepth", soilDepthSuitability);
		physicalSuitabilityMap.put("drainage", drainageSuitableSuitability);

		return SuitabilityResult.of(physicalSuitabilityMap);
	}

	private SuitabilityResult analyzeSoilChemicalSuitability(ProductionCondition productionCondition,
		SoilChemicalResponseDto soilChemicalResponse) {
		if(soilChemicalResponse == null) {
			return null;
		}

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

		return SuitabilityResult.of(chemicalSuitabilityMap);
	}

	private SuitabilityResult analyzeWeatherSuitability(ProductionCondition productionCondition, RegionWeather regionWeather) {
		Map<String, Boolean> weatherSuitabilityMap = new HashMap<>();

		boolean temperatureSuitability = productionCondition.isTemperatureSuitable(regionWeather.getAverageTemperature(),
			regionWeather.getMaxTemperature(), regionWeather.getMinTemperature());
		boolean precipitationSuitability = productionCondition.isRainfallSuitable(regionWeather.getAnnualPrecipitation());
		boolean sunlightSuitability = productionCondition.isSunlightSuitable(regionWeather.getAnnualSunlightHours());

		weatherSuitabilityMap.put("temperature", temperatureSuitability); // 기온
		weatherSuitabilityMap.put("precipitation", precipitationSuitability); // 강수량
		weatherSuitabilityMap.put("sunlightHours", sunlightSuitability); // 일조량

		return SuitabilityResult.of(weatherSuitabilityMap);
	}

}
