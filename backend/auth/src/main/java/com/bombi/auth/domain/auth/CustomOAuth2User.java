package com.bombi.auth.domain.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.bombi.auth.domain.member.Member;

public class CustomOAuth2User implements OAuth2User, UserDetails {

	private Member member;
	private Map<String, Object> attributes;
	private String attributeKey;

	public CustomOAuth2User(Member member, Map<String, Object> attributes, String attributeKey) {
		this.member = member;
		this.attributes = attributes;
		this.attributeKey = attributeKey;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(member.getRole().getRoleName().getDescription()));
	}

	@Override
	public String getName() {
		return attributes.get(attributeKey).toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return member.getAuthEmail();
	}


}
