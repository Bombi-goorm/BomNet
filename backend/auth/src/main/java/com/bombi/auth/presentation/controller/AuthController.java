package com.bombi.auth.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/health")
@RequiredArgsConstructor
public class AuthController {

    @ResponseBody
    @GetMapping
    public ResponseEntity<?> authHealth() {
        return ResponseEntity.ok("Auth :: Healthy");
    }
}
