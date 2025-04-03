package com.bombi.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "member_id", columnDefinition = "VARCHAR(40) NOT NULL")
    @JoinColumn(name = "member_id")
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
