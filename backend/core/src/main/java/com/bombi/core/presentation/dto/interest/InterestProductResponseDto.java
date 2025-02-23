package com.bombi.core.presentation.dto.interest;

import java.util.List;

import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.presentation.dto.product.ProductInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestProductResponseDto {

	// private Long id;
	private List<ProductInfo> products;
	// private String inWatch;

	public InterestProductResponseDto(
		List<Product> products,
		List<Category> midCategories,
		List<Category> lowCategories
	) {
		this.products = products.stream().map(ProductInfo::new).toList();
	}
}
