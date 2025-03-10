package com.bombi.notification.service;

import com.bombi.notification.entity.Member;
import com.bombi.notification.entity.Notification;
import com.bombi.notification.entity.NotificationType;
import com.bombi.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;


    // 알림 배치 Insert
    @Transactional
    public void notificationBatchSave(List<Notification> notifications){
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public Notification createAndSaveNotification(Member member, NotificationType type, String title, String message) {
        Notification notification = Notification.builder()
                .member(member)
                .notificationType(type)
                .title(title)
                .message(message)
                .isRead("N") // 초기 상태: 읽지 않음
                .build();
        return notificationRepository.save(notification);
    }
}
