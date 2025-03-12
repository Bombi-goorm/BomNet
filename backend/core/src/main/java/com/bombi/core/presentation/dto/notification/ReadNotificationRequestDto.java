package com.bombi.core.presentation.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadNotificationRequestDto {

	private Long notificationId;

	public ReadNotificationRequestDto(Long notificationId) {
		this.notificationId = notificationId;
	}
}
