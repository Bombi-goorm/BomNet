package com.bombi.auth.infrastructure.security;

import com.bombi.auth.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class CustomUserDetails implements UserDetails {
    private Member member;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        this.member = member;
        this.authorities = authorities;
    }

    /**
     * 계정 등록 여부 - 소셜 인증 후 등록 진행 여부
     * T : 등록됨
     * N : 미등록
     * @return
     */
    @Override
    public boolean isEnabled() {
        return member.getIsEnabled() != null && member.getIsEnabled().equals('T');
    }

    public Member getMember() {
        return member;
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
        return null;
    }

    /**
     * 계정 잠김 여부
     * T : 잠기지 않음
     * N : 잠김
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.member.getIsBanned() != null && this.member.getIsBanned().equals('T');
    }
}
