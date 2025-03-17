package com.bombi.core.presentation.dto.price;

import java.util.ArrayList;
import java.util.List;

import com.bombi.core.presentation.dto.price.chart.LinkInformation;
import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;
import com.bombi.core.presentation.dto.price.chart.NodeInformation;

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

	public OverallPriceInfoResponse(List<NodeInformation> nodeInformationResponse,
		List<LinkInformation> linkInformationResponse) {
		this.annual = new ArrayList<>();
		this.monthly = new ArrayList<>();
		this.daily = new ArrayList<>();
		this.realTime = new ArrayList<>();
		this.qualityChartData = new ArrayList<>();
		this.regionalChartData = new ArrayList<>();
		this.sankeyData = new SankeyDataResponseDto(nodeInformationResponse, linkInformationResponse);
	}
}
