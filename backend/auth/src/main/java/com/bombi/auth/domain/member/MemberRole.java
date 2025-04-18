package com.bombi.auth.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

	USER("ROLE_USER"),
	FARMER("ROLE_FARMER");

	private final String description;

	public static String getIncludingRoles(String role){
		return MemberRole.valueOf(role).getDescription();
	}
}
