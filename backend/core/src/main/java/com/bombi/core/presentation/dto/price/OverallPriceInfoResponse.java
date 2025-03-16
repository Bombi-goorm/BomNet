package com.bombi.core.presentation.dto.price;

import java.util.List;

import com.bombi.core.presentation.dto.price.chart.LinkInformation;
import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;
import com.bombi.core.presentation.dto.price.chart.TotalNodeInfo;

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

	public OverallPriceInfoResponse(List<TotalNodeInfo> totalNodeInfoResponse,
		List<LinkInformation> linkInformationResponse) {
		this.annual = null;
		this.monthly = null;
		this.daily = null;
		this.realTime = null;
		this.qualityChartData = null;
		this.regionalChartData =null;
		this.sankeyData = new SankeyDataResponseDto(totalNodeInfoResponse, linkInformationResponse);
	}
}
