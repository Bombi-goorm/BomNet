package com.bombi.auth.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private MemberRole roleName;

	private Role(MemberRole memberRole) {
		this.roleName = memberRole;
	}

	public static Role ofUser() {
		return new Role(MemberRole.USER);
	}

	public static Role ofFarmer() {
		return new Role(MemberRole.FARMER);
	}


}
