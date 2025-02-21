package com.bombi.core.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Core :: Healthy";
    }
}
