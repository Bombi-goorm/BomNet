package com.bombi.core.domain.member.model;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PushSubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "push_subscription_id", columnDefinition = "BIGINT")
	private Long id;

	@Column(columnDefinition = "VARCHAR(1024) NOT NULL")
	@Comment("구독 url")
	private String endpoint;

	@Column(columnDefinition = "VARCHAR(128) NOT NULL")
	@Comment("구독 정보")
	private String p256dh;

	@Column(columnDefinition = "VARCHAR(64) NOT NULL")
	@Comment("구독 인증정보")
	private String auth;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("기기 종류")
	private String deviceType;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("os 종류")
	private String os;

	@Column(columnDefinition = "VARCHAR(20) NOT NULL")
	@Comment("브라우저 정보")
	private String browser;

	@JoinColumn(name = "member_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
}
