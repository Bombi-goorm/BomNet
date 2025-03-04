package com.bombi.notification.service;

import com.bombi.notification.entity.NotificationEntity;
import com.bombi.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<NotificationEntity> getNotificationEntity(){
        return notificationRepository.findAll();

    }
}
