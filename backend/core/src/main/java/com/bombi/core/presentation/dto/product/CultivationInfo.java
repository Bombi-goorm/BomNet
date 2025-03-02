package com.bombi.core.presentation.dto.product;

import java.util.List;

import com.bombi.core.domain.cultivation.model.Cultivation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CultivationInfo {

	private String cultivationContent;
	private ProductionConditionResponse conditions;

	public CultivationInfo(Cultivation cultivation) {
		this.cultivationContent = cultivation.getCultivationFeatures();
		this.conditions = new ProductionConditionResponse(cultivation.getProductionCondition());
	}
}
