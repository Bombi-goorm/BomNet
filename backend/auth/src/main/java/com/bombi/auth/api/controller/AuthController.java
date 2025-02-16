package com.bombi.auth.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.bombi.auth.domain.auth.AuthEntity;
import com.bombi.auth.domain.auth.service.AuthService;
import com.bombi.auth.domain.member.Member;

// @RestController
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @ResponseBody
    @GetMapping("/home")
    public ResponseEntity<String> homePage() {
        return ResponseEntity.ok("로그인 성공");
    }

    @ResponseBody
    @GetMapping("/home/info")
    public ResponseEntity<?> homePage(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = authService.getUserInfo(userDetails.getUsername());
        return ResponseEntity.ok(member);
    }

    @ResponseBody
    @GetMapping("/auth")
    public ResponseEntity<?> authHealth() {
        return ResponseEntity.ok("Auth :: Healthy");
    }

    @ResponseBody
    @PostMapping("/auth")
    public ResponseEntity<?> authEntityList(){
        List<AuthEntity> authEntity = authService.getAuthEntity();
        return ResponseEntity.ok(authEntity);
    }
}
