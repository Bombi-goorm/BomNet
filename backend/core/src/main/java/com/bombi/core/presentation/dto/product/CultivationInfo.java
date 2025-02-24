package com.bombi.core.presentation.dto.product;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CultivationInfo {

	private String cultivationContent;
	private List<ProductionConditionResponse> conditions;
}
