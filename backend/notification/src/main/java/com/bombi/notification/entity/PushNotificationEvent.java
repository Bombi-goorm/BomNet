package com.bombi.notification.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationEvent {
    private Long userId;
    private String message;
}