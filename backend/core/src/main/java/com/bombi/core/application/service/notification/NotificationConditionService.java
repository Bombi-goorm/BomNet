package com.bombi.core.application.service.notification;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.notificationcondition.model.NotificationCondition;
import com.bombi.core.domain.notificationcondition.repository.NotificationConditionRepository;
import com.bombi.core.presentation.dto.notification.NotificationConditionResponseDto;
import com.bombi.core.presentation.dto.notification.OffConditionRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConditionService {

	private final NotificationConditionRepository notificationConditionRepository;

	@Transactional
	public void turnOffOne(OffConditionRequestDto requestDto) {
		notificationConditionRepository.deleteById(requestDto.getNotificationConditionId());
	}

	@Transactional(readOnly = true)
	public List<NotificationConditionResponseDto> findActiveConditions(String username) {
		List<NotificationCondition> notificationConditions = notificationConditionRepository.findAllByMember_IdAndActive(
			UUID.fromString(username), "T");
		return notificationConditions.stream().map(NotificationConditionResponseDto::new).toList();
	}
}
