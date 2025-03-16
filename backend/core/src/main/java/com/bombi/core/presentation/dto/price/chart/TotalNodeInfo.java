package com.bombi.core.presentation.dto.price.chart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TotalNodeInfo {

	private String id;
	private String name;

	public TotalNodeInfo(long id, String name) {
		this.id = String.valueOf(id);
		this.name = name;
	}
}
