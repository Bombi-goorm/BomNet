package com.bombi.core.presentation.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QualityVarietyPriceData {

	private String date;
	private String variety;
	private String gradeName;
	private long averagePricePerKg;

	public QualityVarietyPriceData(String date, String variety, String gradeName, long averagePricePerKg) {
		this.date = date;
		this.variety = variety;
		this.gradeName = gradeName;
		this.averagePricePerKg = averagePricePerKg;
	}
}
