package com.bombi.auth.domain.member;

import java.util.UUID;

import com.bombi.auth.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "member_id")
	private UUID id;

	// 소셜로그인 매체
	private String platform;

	// 소셜로그인 이메일
	private String authEmail;

	// 인증 후 등록 전
	private String isEnabled;

	// 탈퇴, 이용정지
	private String isBanned;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	private Member(String platform, String authEmail, Role role) {
		this.platform = platform;
		this.authEmail = authEmail;
		this.role = role;
//		this.isEnabled = "F";
		this.isEnabled = "T"; // 추후 코어서버에서 등록시 변경하도록 수정 필요
		this.isBanned = "F";
	}

	public static Member of(String platform, String authEmail, Role role) {
		return new Member(platform, authEmail, role);
	}
}
