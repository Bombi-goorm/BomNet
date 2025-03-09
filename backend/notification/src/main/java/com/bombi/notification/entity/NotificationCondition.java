package com.bombi.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationCondition extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_condition_id")
	private Long id;

	@Column(columnDefinition = "VARCHAR(50) NOT NULL")
	@Comment("지정가")
	private int targetPrice;

	@Column(columnDefinition = "VARCHAR(1) NOT NULL")
	@Comment("알림 조건 활성화 여부")
	private String active;

	@ManyToOne(fetch = LAZY)
	// @JoinColumn(name = "member_id", columnDefinition = "VARCHAR(40) NOT NULL")
	@JoinColumn(name = "member_id")
	@Comment("멤버 ID")
	private Member member;

	// 가격알림용 임시속성
	private String category;
	private String item;
	private String variety;
	private String region;
}
