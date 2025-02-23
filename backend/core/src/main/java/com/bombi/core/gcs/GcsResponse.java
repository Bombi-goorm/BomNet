package com.bombi.core.gcs;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GcsResponse {
	private List<Item> item;

	@Getter
	@NoArgsConstructor
	public static class Item {
		private String stnId;
		private String title;
		private long tmFc;
		private int tmSeq;
	}
}
