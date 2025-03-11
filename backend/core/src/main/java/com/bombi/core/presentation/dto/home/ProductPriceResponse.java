package com.bombi.core.presentation.dto.home;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductPriceResponse {

	private Long productId;
	private String productName;
	private String imageUrl;
	private List<ProductPriceInfo> productPrices;

	public ProductPriceResponse(long index, String productName, List<ProductPriceInfo> productPriceInfos) {
		this.productId = index;
		this.imageUrl = null;
		this.productName = productName;
		this.productPrices = productPriceInfos;
	}
}
