package com.bombi.core.presentation.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnalyzeResponse {

	private String reason;
	private String suitability; // 적합도
}
