package com.bombi.core.domain.notificationcondition.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bombi.core.domain.notificationcondition.model.NotificationCondition;

public interface NotificationConditionRepository extends JpaRepository<NotificationCondition, Long> {

	List<NotificationCondition> findAllByMember_IdAndActive(UUID memberId, String active);

}
