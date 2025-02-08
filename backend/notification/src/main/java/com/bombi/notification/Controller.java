package com.bombi.notification;

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

    @GetMapping("/notification")
    public ResponseEntity<?> notificationHealth(){
        return ResponseEntity.ok("Notification :: Healthy");
    }

    @PostMapping("/notification")
    public ResponseEntity<?> notificationEntityList(){
        List<NotificationEntity> notificationEntity = notificationService.getNotificationEntity();
        return ResponseEntity.ok(notificationEntity);
    }
}
