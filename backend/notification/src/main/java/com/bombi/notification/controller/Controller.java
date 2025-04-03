package com.bombi.notification.controller;

import com.bombi.notification.service.WebPushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class Controller {

    private final WebPushNotificationService webPushNotificationService;

    @GetMapping("/noti/health")
    public ResponseEntity<?> notificationHealth(){
        return ResponseEntity.ok("Notification :: Healthy");
    }
}
