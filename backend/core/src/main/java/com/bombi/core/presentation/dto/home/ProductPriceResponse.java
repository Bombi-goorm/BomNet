package com.bombi.core.presentation.dto.home;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductPriceResponse {

	private long productId;
	private String productName;
	private String imageUrl;
	private List<ProductPriceInfo> productPrices;

}
