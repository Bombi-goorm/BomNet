package com.bombi.core.presentation.dto.product;

import java.util.List;
import java.util.Map;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SuitabilityResult {

	private String suitability;
	private List<String> unsuitableProperties;

	private SuitabilityResult(String suitability, List<String> unsuitableProperties) {
		this.suitability = suitability;
		this.unsuitableProperties = unsuitableProperties;
	}

	public static SuitabilityResult of(Map<String, Boolean> weatherSuitabilityMap) {
		List<String> unsuitableProperties = weatherSuitabilityMap.entrySet().stream()
			.filter(entry -> !entry.getValue())
			.map(Map.Entry::getKey)
			.toList();

		int unsuitablePropertySize = unsuitableProperties.size();

		if(unsuitablePropertySize== 0) {
			return new SuitabilityResult("적합", unsuitableProperties);
		}

		if(unsuitablePropertySize == 1) {
			return new SuitabilityResult("보통", unsuitableProperties);
		}

		return new SuitabilityResult("부적합", unsuitableProperties);
	}
}
