package com.bombi.core.fasttest.navernews;

import org.springframework.stereotype.Component;

@Component
public class TagEliminator {

	public static String eliminateHtmlTag(String content) {
		return content.replaceAll("</?b>", "").replace("&quot;", "");
	}
}
