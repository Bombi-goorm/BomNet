package com.bombi.auth.domain.member;

import static jakarta.persistence.FetchType.*;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_info_id", columnDefinition = "BIGINT")
	private Long id;

	@Comment("PNU 코드")
	private String pnu;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	@Comment("멤버 ID")
	private Member member;

	public MemberInfo(Member member) {
		this.member = member;
	}
}
