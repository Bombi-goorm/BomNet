package com.bombi.core.infrastructure.external.chart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChartLinkInfo {

	private long source;
	private long target;
	private String value;

	public ChartLinkInfo(long source, long target, String value) {
		this.source = source;
		this.target = target;
		this.value = value;
	}
}
