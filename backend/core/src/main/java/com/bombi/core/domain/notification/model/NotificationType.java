package com.bombi.core.domain.notification.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

	WEATHER("특보 알림"),
	TARGET_PRICE("가격 알림");

	private final String description;
}
