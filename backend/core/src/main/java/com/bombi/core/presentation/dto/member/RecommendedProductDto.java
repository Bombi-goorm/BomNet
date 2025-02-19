package com.bombi.core.presentation.dto.member;

import com.bombi.core.infrastructure.external.bridge.dto.RecommendProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendedProductDto {

	private String productName;
	private String reason;

	public RecommendedProductDto(RecommendProduct recommendProduct) {
		this.productName = recommendProduct.getProductName();
		this.reason = recommendProduct.getReason();
	}
}
