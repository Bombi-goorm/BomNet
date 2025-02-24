package com.bombi.core.presentation.dto.home;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.type.DateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductPriceInfo {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date; // format : 20250203
	private String price;

	public ProductPriceInfo(LocalDate date, String price) {
		this.date = date;
		this.price = price;
	}
}
