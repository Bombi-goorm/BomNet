package com.bombi.core.infrastructure.external.price.chart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChartNodeInfo {

	private long id;
	private String name;

	public ChartNodeInfo(long nodeId, String nodeName) {
		this.id = nodeId;
		this.name = nodeName;
	}

	public void updateId(int id) {
		this.id = id;
	}
}
