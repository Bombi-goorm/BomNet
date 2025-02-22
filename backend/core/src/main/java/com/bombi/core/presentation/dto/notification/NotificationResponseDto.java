package com.bombi.core.presentation.dto.notification;

import java.time.LocalDateTime;

import com.bombi.core.domain.notification.model.Notification;
import com.bombi.core.domain.notification.model.NotificationType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

	private long id;
	private NotificationType type;
	private String content;
	private LocalDateTime createdDate;

	public NotificationResponseDto(Notification notification) {
		this.id = notification.getId();
		this.type = notification.getNotificationType();
		this.content = notification.getMessage();
		this.createdDate = notification.getCreatedDate();
	}


}
