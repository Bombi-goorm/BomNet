package com.bombi.core.infrastructure.external.weather.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialWeatherReport {

	private String stnId; // 관측지점번호
	private String title; // 특보 제목
	private LocalDateTime tmFc; // 발표 시간
	// private String tmFc;


	// public SpecialWeatherReport(String stnId, String title, String tmFc) {
	// 	this.stnId = stnId;
	// 	this.title = title;
	// 	this.tmFc = tmFc;
	// }

	public SpecialWeatherReport(String stnId, String title, LocalDateTime tmFc) {
		this.stnId = stnId;
		this.title = title;
		this.tmFc = tmFc;
	}
}
