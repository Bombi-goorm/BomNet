package com.bombi.core.presentation.dto.price;

import java.util.ArrayList;
import java.util.List;

import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OverallPriceInfoResponse {

	private List<ProductPriceDto> annual;
	private List<ProductPriceDto> monthly;
	private List<ProductPriceDto> daily;
	private List<ProductPriceDto> realTime;
	private List<QualityChartData> qualityChartData;
	private List<RegionChartData> regionalChartData;
	private SankeyDataResponseDto sankeyData;

	public OverallPriceInfoResponse(
		List<ProductPriceDto> annualItemPrice,
		List<ProductPriceDto> monthlyItemPrice,
		List<ProductPriceDto> dailyItemPrice,
		SankeyDataResponseDto sankeyChartInfo)
	{
		this.annual = annualItemPrice;
		this.monthly = monthlyItemPrice;
		this.daily = dailyItemPrice;
		this.realTime = new ArrayList<>();
		this.qualityChartData = new ArrayList<>();
		this.regionalChartData = new ArrayList<>();
		this.sankeyData = sankeyChartInfo;
	}

}
