package com.bombi.core.presentation.dto.price.chart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkInformation {

	private long source;
	private long target;
	private long value;

	public LinkInformation(long source, long target, String value) {
		this.source = source;
		this.target = target;
		this.value = Long.parseLong(value);
	}
}
