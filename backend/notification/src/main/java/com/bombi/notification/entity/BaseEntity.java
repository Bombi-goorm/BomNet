package com.bombi.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL")
	@Comment("생성 시간")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL")
	@Comment("수정 시간")
	private LocalDateTime lastModifiedDate;
}