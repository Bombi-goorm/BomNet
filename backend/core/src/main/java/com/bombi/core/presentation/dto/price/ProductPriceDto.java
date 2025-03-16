package com.bombi.core.presentation.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductPriceDto {
	private long id;
	private String variety;
	private int price;
	private String dateTime;


}
