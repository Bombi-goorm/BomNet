package com.bombi.core.presentation.controller;

import com.bombi.core.application.service.MemberService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;
import com.bombi.core.presentation.dto.member.MemberRequestDto;
import com.bombi.core.presentation.dto.member.MemberResponseDto;

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
    public ResponseEntity<CoreResponseDto<?>> memberInfo(@AuthenticationPrincipal UserDetails userDetails) {
        MemberInfoResponseDto memberInfoResponseDto = memberService.findMemberInfo(userDetails.getUsername());
        return ResponseEntity.ok(CoreResponseDto.ofSuccess("사용자 조회 성공", memberInfoResponseDto));
    }

    @PostMapping
    public ResponseEntity<?> registerMember(
            @RequestBody MemberRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails){
        memberService.registerMember(requestDto, userDetails.);
    }
}
