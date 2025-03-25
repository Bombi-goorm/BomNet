package com.bombi.core.infrastructure.external.weather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum WindStatus {

	WEAK("약함", "F"),
	STRONG("강함", "T");

	private static final Integer STANDARD_SPEED = 9;

	private final String description;
	private final String statusCode;

	public static String findStatusByWindSpeed(String windSpeed) {
		try {
			double windSpeedDouble = Double.parseDouble(windSpeed);
			int windSpeedInt = (int)Math.round(windSpeedDouble);
			return windSpeedInt >= STANDARD_SPEED ? STRONG.getStatusCode() : WEAK.getStatusCode();
		} catch (NumberFormatException e) {
			log.error("유효하지 않은 속도값 : {}", windSpeed);
			return WindStatus.WEAK.getStatusCode();
		}
	}
}
