package com.bombi.auth.presentation.controller;

import com.bombi.auth.domain.member.Member;
import com.bombi.auth.infrastructure.security.CustomOAuth2User;
import com.bombi.auth.infrastructure.RedisService;
import com.bombi.auth.infrastructure.security.CustomUserDetails;
import com.bombi.auth.infrastructure.security.auth.service.CustomUserDetailsService;
import com.bombi.auth.presentation.dto.CommonResponseDto;
import com.bombi.auth.presentation.dto.MemberResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final RedisService redisService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/renew")
    public ResponseEntity<CommonResponseDto<?>> renewToken(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Member member = customUserDetailsService.getMember(customUserDetails.getMember().getId());
        MemberResponseDto responseDto = new MemberResponseDto(
                member.getId(),
                member.getMemberInfo().getPnu()
        );
        return ResponseEntity.ok(new CommonResponseDto<>("200", "토큰 재발행 성공", responseDto));
    }



    @PostMapping("/logout")
    public ResponseEntity<CommonResponseDto<?>> logout(HttpServletResponse response,
                                                 @AuthenticationPrincipal CustomOAuth2User userDetails) {

        ResponseCookie cookie = ResponseCookie.from("access_token", null)
                .secure(true)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        cookie = ResponseCookie.from("refresh_token", null)
                .secure(true)
                .httpOnly(true)
                .path("/")
                .sameSite("None")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        redisService.resetStatus(String.valueOf(userDetails.getMember().getId()));

        return ResponseEntity.ok(new CommonResponseDto<>("200", "로그아웃 성공"));
    }
}
