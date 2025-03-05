package com.bombi.notification.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_info_id", columnDefinition = "BIGINT")
	private Long id;

	private String pnu;

	@OneToOne(fetch = LAZY)
	// @JoinColumn(name = "member_id", columnDefinition = "VARCHAR(40) NOT NULL")
	@JoinColumn(name = "member_id")
	@Comment("멤버 ID")
	private Member member;


}
