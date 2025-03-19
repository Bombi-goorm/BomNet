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
		List<ProductPriceDto> realTimeItemPrice,
		List<RegionChartData> regionItemPrice,
		SankeyDataResponseDto sankeyChartInfo)
	{
		this.annual = annualItemPrice;
		this.monthly = monthlyItemPrice;
		this.daily = dailyItemPrice;
		this.realTime = realTimeItemPrice;
		this.qualityChartData = new ArrayList<>();
		this.regionalChartData = regionItemPrice;
		this.sankeyData = sankeyChartInfo;
	}

}
