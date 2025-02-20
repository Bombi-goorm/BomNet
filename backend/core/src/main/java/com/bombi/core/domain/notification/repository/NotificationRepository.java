package com.bombi.core.domain.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

import com.bombi.core.domain.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByMember_IdOrderByCreatedDateDesc(Long memberId);

    Page<Notification> findNotificationsByMember_IdOrderByCreatedDateDesc(UUID memberId, Pageable pageable);
}
