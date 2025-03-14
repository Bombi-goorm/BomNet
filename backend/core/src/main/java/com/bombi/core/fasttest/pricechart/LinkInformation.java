package com.bombi.core.fasttest.pricechart;

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

	public static LinkInformation of(SankeyLinkDto sankeyLinkDto) {
		return new LinkInformation(sankeyLinkDto.getSource(), sankeyLinkDto.getTarget(), sankeyLinkDto.getValue());
	}

}
