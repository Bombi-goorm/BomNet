package com.bombi.core.presentation.dto.notification;

import java.time.LocalDateTime;

import com.bombi.core.domain.notification.model.Notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

	private long id;
	private String title;
	private String content;
	private String isRead;
	private LocalDateTime createDate;

	public NotificationResponseDto(Notification notification) {
		this.id = notification.getId();
		this.title = notification.getNotificationType().getDescription();
		this.content = notification.getMessage();
		this.isRead = notification.getIsRead();
		this.createDate = notification.getCreatedDate();
	}


}
