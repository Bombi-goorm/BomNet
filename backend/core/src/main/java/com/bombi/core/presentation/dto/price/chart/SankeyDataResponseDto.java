package com.bombi.core.presentation.dto.price.chart;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SankeyDataResponseDto {

	private List<TotalNodeInfo> nodes;
	private List<LinkInformation> links;

	public SankeyDataResponseDto(List<TotalNodeInfo> nodes, List<LinkInformation> links) {
		this.nodes = nodes;
		this.links = links;
	}
}
