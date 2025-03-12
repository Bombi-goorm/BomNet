package com.bombi.core.domain.notification.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

import com.bombi.core.domain.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findNotificationsByMember_IdOrderByCreatedDateDesc(UUID memberId, Pageable pageable);

    Page<Notification> findAllByMember_IdAndIsReadOrderByCreatedDateDesc(UUID memberId, String isRead, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Notification n SET n.isRead = :isRead WHERE n.member.id = :memberId")
    void updateAllNotificationsByMember_IdAndIsRead(UUID memberId, String isRead);
}
