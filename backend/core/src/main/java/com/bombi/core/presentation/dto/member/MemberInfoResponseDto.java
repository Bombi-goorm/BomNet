package com.bombi.core.presentation.dto.member;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponseDto {

	private String memberId;
	private String email;
	private String bjdCode;
	private String pnuCode;
	private FarmInfoResponseDto farmInfoResponseDto;
	private List<RecommendedProductDto> recommendedProducts;



}
