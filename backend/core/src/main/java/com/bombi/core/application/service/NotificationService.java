package com.bombi.core.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import com.bombi.core.domain.notification.model.Notification;
import com.bombi.core.domain.notification.repository.NotificationRepository;
import com.bombi.core.presentation.dto.notification.NotificationResponseDto;
import com.bombi.core.presentation.dto.notification.ReadNotificationRequestDto;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;

	@Transactional(readOnly = true)
	public List<NotificationResponseDto> getNotifications(Pageable pageable, String username) {
		UUID memberId = UUID.fromString(username);
		Page<Notification> notificationPage = notificationRepository.findAllByMember_IdAndIsReadOrderByCreatedDateDesc(
			memberId, "F", pageable);

		return notificationPage.map(NotificationResponseDto::new).getContent();
	}

	@Transactional
	public void markAsRead(ReadNotificationRequestDto requestDto) {
		Notification notification = notificationRepository.findById(requestDto.getId())
			.orElseThrow(() -> new IllegalArgumentException("조회된 알림이 없습니다."));

		notification.read();
	}

	@Transactional
	public void markAsReadAllNotification(String username) {
		notificationRepository.updateAllNotificationsByMember_IdAndIsRead(UUID.fromString(username), "T");
	}

}
