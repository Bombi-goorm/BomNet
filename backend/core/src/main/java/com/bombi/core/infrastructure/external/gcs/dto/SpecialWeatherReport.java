package com.bombi.core.infrastructure.external.gcs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialWeatherReport {

	private String stnId; // 관측지점번호
	private String title; // 특보 제목
	private long tmFc; // 발표 시간
	private int tmSeq; // 발표 번호


}
