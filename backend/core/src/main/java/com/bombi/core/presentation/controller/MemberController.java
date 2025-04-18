package com.bombi.core.presentation.controller;

import com.bombi.core.application.service.member.MemberService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.infrastructure.security.authentication.CustomUserDetails;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;
import com.bombi.core.presentation.dto.member.MemberRequestDto;
import com.bombi.core.presentation.dto.member.MemberResponseDto;
import com.bombi.core.presentation.dto.member.PnuRegisterRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<CoreResponseDto<?>> memberInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("memberInfo-ID::" + userDetails.getUsername());
        MemberInfoResponseDto memberInfoResponseDto = memberService.findMemberInfo(userDetails.getUsername());
        return ResponseEntity.ok(CoreResponseDto.ofSuccess("사용자 조회 성공", memberInfoResponseDto));
    }

    @PostMapping("/pnu")
    public ResponseEntity<?> registerPnu(@RequestBody PnuRegisterRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        memberService.registerPnu(requestDto, userDetails.getUsername());
        return ResponseEntity.ok((CoreResponseDto.ofSuccess("pnu 등록 완료")));
    }

}
