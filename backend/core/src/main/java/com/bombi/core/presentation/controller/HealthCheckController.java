package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core/health")
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<?> coreHealth() {
        return ResponseEntity.ok("Core :: Healthy");
    }
}
