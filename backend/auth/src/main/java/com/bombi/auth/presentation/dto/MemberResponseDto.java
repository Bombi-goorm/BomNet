package com.bombi.auth.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private UUID memberId;
    private String pnu;
}
