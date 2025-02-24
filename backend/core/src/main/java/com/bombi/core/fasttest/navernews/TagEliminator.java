package com.bombi.core.fasttest.navernews;

import org.springframework.stereotype.Component;

@Component
public class TagEliminator {

	public static String eliminateBTag(String content) {
		String replace = content.replace("<[^>]*>", "");
		return replace;
	}
}
