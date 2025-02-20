package com.bombi.core.domain.notification.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import com.bombi.core.domain.base.model.BaseEntity;
import com.bombi.core.domain.member.model.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", columnDefinition = "VARCHAR(40) NOT NULL")
    @Comment("멤버 ID")
    private Member member;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    @Comment("알림 종류")
    private NotificationType notificationType;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '알림 제목'")
    private String title;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '알림 내용'")
    private String message;

    @Column(columnDefinition = "VARCHAR(1) COMMENT '알림 확인 여부'")
    private String isRead;


}
