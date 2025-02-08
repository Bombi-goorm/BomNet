package com.bombi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final AuthService authService;

    @GetMapping("/auth")
    public ResponseEntity<?> authHealth(){
        return ResponseEntity.ok("Auth :: Healthy");
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authEntityList(){
        List<AuthEntity> authEntity = authService.getAuthEntity();
        return ResponseEntity.ok(authEntity);
    }
}
