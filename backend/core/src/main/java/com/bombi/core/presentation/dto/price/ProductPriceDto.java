package com.bombi.core.presentation.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductPriceDto {
	private long id;
	private String variety;
	private long price;
	private String dateTime;

	public ProductPriceDto(long id, String variety, long price, String dateTime) {
		this.id = id;
		this.variety = variety;
		this.price = price;
		this.dateTime = dateTime;
	}
}
