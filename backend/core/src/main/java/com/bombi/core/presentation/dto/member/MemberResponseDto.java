package com.bombi.core.presentation.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String tel;
    private String birth;
    private String userFinanceId;
}
