package com.bombi.core.presentation.dto.member;

import java.util.List;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.notificationcondition.model.NotificationCondition;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.presentation.dto.notification.NotificationConditionResponseDto;
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
	private FarmInfoResponseDto myFarm;
	private List<RecommendedProductDto> recommendedProducts;
	private List<NotificationConditionResponseDto> notificationConditions;

	public MemberInfoResponseDto(MemberInfo memberInfo, RegionWeather regionWeather,
		BigQueryRecommendProductResponseDto responseDto, List<NotificationCondition> notificationConditions) {
		this.memberId = memberInfo.getMember().getId().toString();
		this.email = memberInfo.getMember().getAuthEmail();
		this.pnuCode = memberInfo.getPnu();
		// this.farmInfoResponseDto = new FarmInfoResponseDto(regionWeather);
		this.recommendedProducts = null;
		this.notificationConditions = notificationConditions.stream()
			.map(NotificationConditionResponseDto::new)
			.toList();
	}

	public MemberInfoResponseDto(
		Member member,
		RegionWeather regionWeather,
		SoilCharacterResponseDto characterResponseDto,
		SoilChemicalResponseDto chemicalResponseDto,
		BigQueryRecommendProductResponseDto recommendProductResponseDto)
	{
		this.memberId = member.getId().toString();
		this.email = member.getAuthEmail();
		this.pnuCode = member.getMemberInfo().getPnu();
		this.myFarm = new FarmInfoResponseDto(regionWeather, characterResponseDto, chemicalResponseDto);
		this.recommendedProducts = null;
		this.notificationConditions = member.getNotificationConditions().stream()
			.map(NotificationConditionResponseDto::new)
			.toList();
	}
}
