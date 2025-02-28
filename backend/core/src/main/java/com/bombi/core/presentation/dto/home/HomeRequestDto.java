package com.bombi.core.presentation.dto.home;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HomeRequestDto {
    private String endPoint;
    private String p256dh;
    private String auth;
    private String deviceType;
}
