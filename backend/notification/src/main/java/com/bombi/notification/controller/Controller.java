package com.bombi.notification.controller;

import com.bombi.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final NotificationService notificationService;

    @GetMapping("/noti/health")
    public ResponseEntity<?> notificationHealth(){
        return ResponseEntity.ok("Notification :: Healthy");
    }
}
