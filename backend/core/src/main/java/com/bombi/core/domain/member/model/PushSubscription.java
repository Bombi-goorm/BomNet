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
import lombok.Builder;
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

	@Builder
	private PushSubscription(String endpoint, String p256dh, String auth, String deviceType, String os, String browser,
		Member member) {
		this.endpoint = endpoint;
		this.p256dh = p256dh;
		this.auth = auth;
		this.deviceType = deviceType;
		this.os = os;
		this.browser = browser;
		this.member = member;
	}

	public static PushSubscription of(String endpoint, String p256dh, String auth, String deviceType, String os, String browser,
		Member member) {
		return PushSubscription.builder()
			.endpoint(endpoint)
			.p256dh(p256dh)
			.auth(auth)
			.deviceType(deviceType)
			.os(os)
			.browser(browser)
			.member(member)
			.build();
	}

	public void updateOs(String osName) {
		this.os = osName;
	}

	public void updateBrowser(String browserName) {
		this.browser = browserName;
	}

	public void updateAuth(String auth) {
		this.auth = auth;
	}

	public void updateP256dh(String p256dh) {
		this.p256dh = p256dh;
	}

	public void updateEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
