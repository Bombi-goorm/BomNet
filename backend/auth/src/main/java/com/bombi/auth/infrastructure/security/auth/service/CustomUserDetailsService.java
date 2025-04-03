package com.bombi.auth.infrastructure.security.auth.service;


import com.bombi.auth.application.exception.e500.RedisSessionException;
import com.bombi.auth.domain.member.MemberInfo;
import com.bombi.auth.infrastructure.security.CustomOAuth2User;
import com.bombi.auth.infrastructure.security.CustomUserDetails;
import com.bombi.auth.domain.member.Member;
import com.bombi.auth.domain.member.Role;
import com.bombi.auth.domain.member.repository.MemberRepository;
import com.bombi.auth.domain.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService extends DefaultOAuth2UserService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    /**
     * 일반 인증 처리
     * */
    @Override
    public CustomUserDetails loadUserByUsername(String memberId) throws IllegalArgumentException {
        Optional<Member> savedMember = memberRepository.findById(UUID.fromString(memberId));

        if(savedMember.isEmpty()){
            log.error("사용자 조회 실패 - memberId={}", memberId);
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        Member member = savedMember.get();

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new CustomUserDetails(member, authorities);
    }

    /**
     * OAuth2 로그인 처리
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
        if (email == null) {
            log.error("OAuth2 로그인 실패 - 카카오 이메일 정보 없음");
            throw new OAuth2AuthenticationException("카카오 이메일 정보 없음");
        }

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // DB에서 사용자 찾기 or 새로 저장
        Member savedMember = getOrSaveMember(registrationId, email);

        // OAuth2 사용자 생성 (권한 추가)
        return new CustomOAuth2User(savedMember, attributes);
    }



    /**
     * 이메일로 기존 유저를 찾거나, 없으면 새로 저장
     */
    private Member getOrSaveMember(String platform, String email) {
        return memberRepository.findByAuthEmail(email)
                .orElseGet(() -> {
                    Optional<Role> userRole = roleRepository.findByRoleName("ROLE_USER");
                    if (userRole.isEmpty()) {
                        log.error("사용자 권한 조회 실패 - ROLE_USER 없음");
                        throw new RedisSessionException("사용자 권한 확인 실패");
                    }
                    try {
                        Member newMember = Member.of(platform, email, userRole.get());
                        MemberInfo memberInfo = new MemberInfo(newMember);
                        newMember.updateMemberInfo(memberInfo);
                        return memberRepository.saveAndFlush(newMember);
                    } catch (Exception e) {
                        log.error("새 사용자 저장 실패 - platform={}", platform, e);
                        throw new RedisSessionException("사용자 저장 중 오류 발생");
                    }
                });
    }

    public Member getMember(UUID memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("사용자 조회 실패 - memberId={}", memberId);
                    return new UsernameNotFoundException("Member not found");
                });
    }
}
