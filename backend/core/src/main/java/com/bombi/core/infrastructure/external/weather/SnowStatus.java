package com.bombi.core.infrastructure.external.weather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SnowStatus {

	NORMAL("1", "1cm 미만"),
	MANY("2", "1cm 이상");


	private final String code;
	private final String description;
}
