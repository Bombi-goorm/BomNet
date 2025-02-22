package com.bombi.core.domain.notification.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

	WEATHER("특보"),
	TARGET_PRICE("지정가");

	private final String description;
}
