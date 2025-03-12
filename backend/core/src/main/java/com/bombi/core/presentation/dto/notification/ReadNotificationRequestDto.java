package com.bombi.core.presentation.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadNotificationRequestDto {

	private Long id;

	public ReadNotificationRequestDto(Long id) {
		this.id = id;
	}
}
