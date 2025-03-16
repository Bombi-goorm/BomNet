package com.bombi.core.presentation.dto.price.chart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LinkInformation {

	private String source;
	private String target;
	private String value;

	public LinkInformation(String source, String target, String value) {
		this.source = source;
		this.target = target;
		this.value = value;
	}

	public LinkInformation(long source, long target, String value) {
		this.source = String.valueOf(source);
		this.target = String.valueOf(target);
		this.value = value;
	}
}
