package com.bombi.core.domain.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.bombi.core.domain.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findNotificationsByMember_IdOrderByCreatedDateDesc(UUID memberId, Pageable pageable);
}
