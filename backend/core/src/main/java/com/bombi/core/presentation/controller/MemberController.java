package com.bombi.core.presentation.controller;

import com.bombi.core.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taco/core/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * ID로 특정 Member 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(new CoreResponseDto<>("SUCCESS", "사용자 조회 성공", memberService.findMemberById(id)));
    }

    /**
     * 회원 탈퇴
     */
    @PutMapping("/{id}/deactivation")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        memberService.deactivateMember(id);

        // 쿠키 삭제를 위한 ResponseCookie 생성
        ResponseCookie cookie = ResponseCookie.from("Authorization", "")
                .path("/")
                .httpOnly(true)
                .maxAge(0) // 쿠키 만료
                .build();

        // 응답에 쿠키 추가
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString()) // Set-Cookie 헤더 추가
                .body(new CoreResponseDto<>("SUCCESS", "회원 탈퇴가 완료되었습니다."));
    }
}
