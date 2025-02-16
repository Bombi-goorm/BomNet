package com.bombi.auth.domain.auth.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bombi.auth.domain.auth.CustomOAuth2User;
import com.bombi.auth.domain.member.Member;
import com.bombi.auth.domain.member.MemberRole;
import com.bombi.auth.domain.member.Role;
import com.bombi.auth.domain.member.repository.MemberRepository;
import com.bombi.auth.domain.member.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		Map<String, Object> attributes = oAuth2User.getAttributes();
		Map<String, Object> account = (Map<String, Object>)attributes.get("kakao_account");
		String accountEmail = (String)account.get("email");

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String attributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		Member savedMember = getOrSaveMember(registrationId, accountEmail);

		return new CustomOAuth2User(savedMember, attributes, attributeName);
	}

	private Member getOrSaveMember(String platform, String email) {
		return memberRepository.findByAuthEmail(email)
			.orElseGet(() -> {
				Role userRole = getOrSaveRole();
				Member member = Member.of(platform, email, userRole);
				return memberRepository.saveAndFlush(member);
			});
	}

	private Role getOrSaveRole() {
		return roleRepository.findByRoleName(MemberRole.USER)
			.orElseGet(() -> {
				Role role = Role.ofUser();
				return roleRepository.save(role);
			});
	}
}
