package com.bombi.core.presentation.dto.price.chart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NodeInformation {

	private String id;
	private String name;
	private int depth;

	public NodeInformation(long id, String name) {
		this.id = String.valueOf(id);
		this.name = name;
	}

	public NodeInformation(long id, String name, int depth) {
		this.id = String.valueOf(id);
		this.name = name;
		this.depth = depth;
	}
}
