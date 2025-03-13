package com.bombi.core.presentation.dto.member;

import com.bombi.core.infrastructure.external.bigquery.dto.RecommendProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendedProductDto {

	private String id;
	private String name;
	private String reason;

	public RecommendedProductDto(RecommendProduct recommendProduct) {
		this.id = null;
		this.name = recommendProduct.getProductName();
		this.reason = recommendProduct.getReason();
	}
}
