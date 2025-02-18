package com.bombi.core.application.service;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;
import com.bombi.core.presentation.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberInfoResponseDto findMemberInfo(String memberId) {
        // MemberInfoResponseDto : memberId, email, bjd, pnu -> member + memberInfo


        // FarmInfoResponseDto : sido, destrict -> region,
        // soilType, avgTemp, annualPrecipitation -> weather api

        // RecommendedProductDto : bigquery
        return null;
    }
}
