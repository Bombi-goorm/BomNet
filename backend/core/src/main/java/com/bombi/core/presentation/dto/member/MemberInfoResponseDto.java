package com.bombi.core.presentation.dto.member;

import java.util.List;

import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.infrastructure.external.bridge.dto.BridgeRecommendProductResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponseDto {

	private String memberId;
	private String email;
	private String pnuCode;
	private FarmInfoResponseDto farmInfoResponseDto;
	private List<RecommendedProductDto> recommendedProducts;

	public MemberInfoResponseDto(MemberInfo memberInfo, RegionWeather regionWeather,
		BridgeRecommendProductResponseDto responseDto) {
		this.memberId = memberInfo.getMember().getId().toString();
		this.email = memberInfo.getMember().getEmail();
		this.pnuCode = memberInfo.getPnu();
		this.farmInfoResponseDto = new FarmInfoResponseDto(regionWeather);
		this.recommendedProducts = responseDto.getRecommendProducts()
			.stream()
			.map(RecommendedProductDto::new)
			.toList();
		// this.recommendedProducts = null;
	}
}
