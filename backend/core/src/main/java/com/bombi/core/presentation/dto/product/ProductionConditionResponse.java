package com.bombi.core.presentation.dto.product;

import com.bombi.core.domain.productionCondition.model.ProductionCondition;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductionConditionResponse {

	private String overwintering; // 월동 여부(T: 가능, F: 불가능)
	private String avgTemperatureC; // 평균기온
	private String minTemperatureC; // 최저 기온
	private String maxTemperatureC; // 최고 기온
	private String annualRainfallMM; // 연평균 강수량
	private String sunlightHours; // 일조량
	private String drainage; // 배수 등급("양호", "불량" 등)
	private String soilDepth; // 유효 토심(cm)
	private String pH; // 토양 산도(pH)

	public ProductionConditionResponse(ProductionCondition condition) {
		this.overwintering = condition.getOverwintering();
		this.avgTemperatureC = condition.getAverageTemperature();
		this.minTemperatureC = condition.getMinTemperature();
		this.maxTemperatureC = condition.getMaxTemperature();
		this.annualRainfallMM = condition.getAnnualRainfall();
		this.sunlightHours = condition.getSunlightHours();
		this.drainage = condition.getDrainage();
		this.soilDepth = condition.getSoilDepth();
		this.pH = condition.getPh();
	}
}
