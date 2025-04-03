package com.bombi.core.presentation.dto.product;

import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.product.model.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductInfo {

	private long productId;
	private String imageUrl;
	// private long categoryLargeId;
	// private String categoryLargeName;
	private long categoryMidId;
	private String categoryMidName;
	private long categorySmallId;
	private String categorySmallName;

	public ProductInfo(Product product) {
		Category smallCategory = product.getCategory();
		Category midCategory = smallCategory.getParent();

		this.productId = product.getId();
		this.imageUrl = product.getImageUrl();
		this.categoryMidId = midCategory.getId();
		this.categoryMidName = midCategory.getName();
		this.categorySmallId = smallCategory.getId();
		this.categorySmallName = smallCategory.getName();
	}
}
