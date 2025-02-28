package com.bombi.core.presentation.dto.product;

import com.bombi.core.domain.product.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchResponseDto {

	private ProductInfo product;
	private CultivationInfo cultivationInfo;
	private ProductSales sales;
	private FarmSuitability farmSuitability;

	public ProductSearchResponseDto(Product product) {
		this.product = new ProductInfo(product);
		this.cultivationInfo = new CultivationInfo(product.getCultivation());
		this.sales = null;
		this.farmSuitability = null;
	}
}
