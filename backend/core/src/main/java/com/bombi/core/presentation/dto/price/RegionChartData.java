package com.bombi.core.presentation.dto.price;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegionChartData {

	private String variety;
	private String region;
	private long price;

	public RegionChartData(String variety, String region, long price) {
		this.variety = variety;
		this.region = region;
		this.price = price;
	}
}
