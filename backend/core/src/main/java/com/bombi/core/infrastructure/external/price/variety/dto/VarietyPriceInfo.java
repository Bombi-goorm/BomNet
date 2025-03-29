package com.bombi.core.infrastructure.external.price.variety.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VarietyPriceInfo {

	private String market;
	private String variety;
	private String dateTime;
	private long averagePricePerKg;

	public VarietyPriceInfo(String variety, String dateTime, long averagePricePerKg) {
		this.variety = variety;
		this.dateTime = dateTime;
		this.averagePricePerKg = averagePricePerKg;
	}

	public VarietyPriceInfo(String market, String variety, String dateTime, long averagePricePerKg) {
		this.market = market;
		this.variety = variety;
		this.dateTime = dateTime;
		this.averagePricePerKg = averagePricePerKg;
	}
}
