package com.bombi.core.infrastructure.security.authentication;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bombi.core.domain.member.model.Member;

public class CustomUserDetails implements UserDetails {

	private Member member;
	private Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(Member member, List<SimpleGrantedAuthority> authorities) {
		this.member = member;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return member.getId().toString();
	}

	@Override
	public boolean isAccountNonLocked() {
		return member.getIsBanned() != null && member.getIsBanned().equals("T");
	}

	@Override
	public boolean isEnabled() {
		return member.getIsEnabled() != null && member.getIsEnabled().equals("T");
	}

}
