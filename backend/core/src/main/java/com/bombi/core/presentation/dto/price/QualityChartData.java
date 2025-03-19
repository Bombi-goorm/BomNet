package com.bombi.core.presentation.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QualityChartData {

	private String date;
	private String variety;
	private int special;
	private int high;
	private int moderate;
	private int other;

	public QualityChartData(String date, String variety, int special, int high, int moderate, int other) {
		this.date = date;
		this.variety = variety;
		this.special = special;
		this.high = high;
		this.moderate = moderate;
		this.other = other;
	}
}
