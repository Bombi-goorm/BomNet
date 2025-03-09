package com.bombi.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertMessage {
    private UUID userId;
    private String variety;
    private String markets;
    private String price;
}