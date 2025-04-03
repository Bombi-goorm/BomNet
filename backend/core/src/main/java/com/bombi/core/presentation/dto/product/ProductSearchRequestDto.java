package com.bombi.core.presentation.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductSearchRequestDto {

	private String item;
	private String variety;
	private String pnu;
}
