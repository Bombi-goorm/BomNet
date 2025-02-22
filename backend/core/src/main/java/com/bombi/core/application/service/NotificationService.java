package com.bombi.core.application.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.bombi.core.domain.notification.model.Notification;
import com.bombi.core.domain.notification.repository.NotificationRepository;
import com.bombi.core.presentation.dto.notification.NotificationResponseDto;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;

	public List<NotificationResponseDto> getNotifications(Pageable pageable, String username) {
		UUID memberId = UUID.fromString(username);
		Page<Notification> notificationPage = notificationRepository.findNotificationsByMember_IdOrderByCreatedDateDesc(
			memberId, pageable);

		return notificationPage.map(NotificationResponseDto::new).getContent();
	}


    //
    // /**
    //  * 알림 생성하고 저장하기
    //  */
    // @Transactional
    // public void sendNotification(Member member, String message) {
    //     Notification notification = new Notification();
    //     notification.saveMember(member);
    //     notification.saveMessage(message);
    //     notificationRepository.save(notification);
    // }
    //
    // /**
    //  * 알림 조회
    //  */
    // public List<NotificationResponseDto> getNotificationsForMember(Long memberId) {
    //     List<Notification> notifications = notificationRepository.findAllByMember_IdOrderByCreatedDateDesc(memberId);
    //     return notifications.stream()
    //             .map(notification -> new NotificationResponseDto(
    //                     notification.getMember().getId()
    //                     , notification.getId()
    //                     , notification.getMessage()
    //                     , notification.getCreatedDate()))
    //             .collect(Collectors.toList());
    //
    // }
    //
    // /**
    //  * 알림 읽음 처리
    //  */
    // public void markAsRead(Long notificationId) {
    //     // 알림 내역에 있는지 확인
    //     Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new IllegalArgumentException("조회된 알림이 없습니다."));
    //
    //     // 알림 여부 업데이트
    //     if (!"Y".equals(notification.getIsRead())) {
    //         notification.updateIsRead("Y");
    //         notificationRepository.save(notification);
    //     }
    // }
}
