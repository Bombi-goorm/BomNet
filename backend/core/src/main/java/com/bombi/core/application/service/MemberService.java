package com.bombi.core.application.service;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.presentation.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * ID로 Member 조회
     */
    public MemberResponseDto findMemberById(Long id) {
        if (id == null || id < 1) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        Member member = memberRepository.findByIdAndDeleted(id, "N")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return new MemberResponseDto(
                member.getId(),
                member.getEmail(),
        );

    /**
     * 회원정보 수정
     */
    public void updateMemberTel(Long memberId, UpdateMemberRequestDto requestDto) {
        if (memberId == null || memberId < 1) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }
        // 회원 정보 확인
        Member member = memberRepository.findByIdAndDeleted(memberId, "N")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        // 문자 인증 성공 세션 확인
        if (!smsAuthUtil.isVerificationSuccessful(requestDto.getTel(), "tel", memberId)) {
            throw new IllegalArgumentException("본인 인증 성공 내역이 없습니다. 본인 인증이 완료되어야 전화번호 수정이 가능합니다.");
        }

        memberRepository.save(member);
    }

    /**
     * 회원 탈퇴
     */
    public void deactivateMember(Long id){
            if (id == null || id < 1) {
                throw new IllegalArgumentException("회원 정보가 없습니다.");
            }
            Member member = memberRepository.findByIdAndDeleted(id, "N")
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 이미 탈퇴한 회원입니다."));

            member.deactivate(); // 회원상태 비활성화
            memberRepository.save(member);
    }
}
