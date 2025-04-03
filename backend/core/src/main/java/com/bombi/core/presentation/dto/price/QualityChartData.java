package com.bombi.core.presentation.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QualityChartData {

	private String date;
	private String variety;
	private Long special;
	private Long high;
	private Long moderate;
	private Long other;

	public QualityChartData(String date, String variety, Long special, Long high, Long moderate, Long other) {
		this.date = date;
		this.variety = variety;
		this.special = special;
		this.high = high;
		this.moderate = moderate;
		this.other = other;
	}
}
