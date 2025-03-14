package com.bombi.core.fasttest.pricechart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PriceChartNodeResponse {

	private List<ChartNodeInfo> chartNodeInfos;

	public PriceChartNodeResponse(List<ChartNodeInfo> chartNodeInfos) {
		this.chartNodeInfos = chartNodeInfos;
	}


}
