package com.bombi.notification.service;

import com.bombi.notification.entity.Member;
import com.bombi.notification.entity.Notification;
import com.bombi.notification.entity.NotificationType;
import com.bombi.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;


    // 알림 배치 Insert
    @Transactional
    public void notificationBatchSave(List<Notification> notifications){
        try {
            notificationRepository.saveAll(notifications);
            log.debug("알림 배치 저장 성공 - count={}", notifications.size());
        } catch (Exception e) {
            log.error("알림 배치 저장 실패 - count={}", notifications.size(), e);
            // 필요시 throw로 상위 처리
            throw e;
        }
    }

    @Transactional
    public Notification createAndSaveNotification(Member member, NotificationType type, String title, String message) {
        try {
            Notification notification = Notification.builder()
                    .member(member)
                    .notificationType(type)
                    .title(title)
                    .message(message)
                    .isRead("F")
                    .build();
            Notification saved = notificationRepository.save(notification);
            log.debug("알림 저장 성공 - memberId={}, type={}", member.getId(), type);
            return saved;
        } catch (Exception e) {
            log.error("알림 생성 및 저장 실패 - memberId={}, type={}", member.getId(), type, e);
            throw e;
        }
    }
}
