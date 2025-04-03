package com.bombi.core.presentation.dto.notification;

import com.bombi.core.domain.notificationcondition.model.NotificationCondition;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationConditionResponseDto {

	private Long id;
	private String item;
	private String variety;
	private String markets;
	private String price;
	private String isActive;

	public NotificationConditionResponseDto(NotificationCondition notificationCondition) {
		this.id = notificationCondition.getId();
		this.item = notificationCondition.getItem();
		this.variety = notificationCondition.getVariety();
		this.markets = notificationCondition.getRegion();
		this.price = String.valueOf(notificationCondition.getTargetPrice());
		this.isActive = notificationCondition.getActive();
	}
}
