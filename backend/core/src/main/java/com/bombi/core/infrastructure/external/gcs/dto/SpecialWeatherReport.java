package com.bombi.core.infrastructure.external.gcs.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpecialWeatherReport {

	private String stnId; // 관측지점번호
	private String title; // 특보 제목
	private LocalDateTime tmFc; // 발표 시간

	public SpecialWeatherReport(String stnId, String title, LocalDateTime tmFc) {
		this.stnId = stnId;
		this.title = title;
		this.tmFc = tmFc;
	}
}
