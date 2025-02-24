package com.bombi.core.presentation.dto.home;

import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BestItem {

	private List<ProductPriceResponse> products;

	public BestItem(List<ProductPriceResponse> productPriceResponses) {
		this.products = productPriceResponses;
	}
}
