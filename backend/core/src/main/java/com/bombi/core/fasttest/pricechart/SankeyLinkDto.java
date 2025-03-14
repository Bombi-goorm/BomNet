package com.bombi.core.fasttest.pricechart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SankeyLinkDto {

	private String name;
	private String source;
	private String target;
	private String value;

	public SankeyLinkDto(String source, String target, String value) {
		this.source = source;
		this.target = target;
		this.value = value;
	}

	public SankeyLinkDto(String name, String source, String target, String value) {
		this.name = name;
		this.source = source;
		this.target = target;
		this.value = value;
	}
}
