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
}
