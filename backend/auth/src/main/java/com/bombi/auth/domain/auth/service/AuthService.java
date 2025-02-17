package com.bombi.auth.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.bombi.auth.domain.auth.AuthEntity;
import com.bombi.auth.domain.auth.repository.AuthRepository;
import com.bombi.auth.domain.member.Member;
import com.bombi.auth.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
	private final MemberRepository memberRepository;

    public List<AuthEntity> getAuthEntity(){
        return authRepository.findAll();

    }

	@Transactional(readOnly = true)
	public Member getUserInfo(String email) {
		return memberRepository.findByAuthEmail(email)
			.orElseThrow(IllegalArgumentException::new);
	}
}
