package com.bombi.core.presentation.dto.member;

import java.time.LocalDateTime;
import java.util.List;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.notificationcondition.model.NotificationCondition;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilSectionResponseDto;
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
	private LocalDateTime joinDate;
	private String pnu;
	private FarmInfoResponseDto myFarm;
	private List<NotificationConditionResponseDto> notificationConditions;

	public MemberInfoResponseDto(
		Member member,
		RegionWeather regionWeather,
		SoilCharacterResponseDto characterResponseDto,
		SoilChemicalResponseDto chemicalResponseDto,
		SoilSectionResponseDto sectionResponseDto)
	{
		this.memberId = member.getId().toString();
		this.email = member.getAuthEmail();
		this.joinDate = member.getCreatedDate();
		this.pnu = member.getMemberInfo().getPnu();
		this.myFarm = new FarmInfoResponseDto(regionWeather, characterResponseDto, chemicalResponseDto, sectionResponseDto);
		this.notificationConditions = member.getNotificationConditions().stream()
			.filter(NotificationCondition::isActive)
			.map(NotificationConditionResponseDto::new)
			.toList();
	}

	public MemberInfoResponseDto(Member member) {
		this.memberId = member.getId().toString();
		this.email = member.getAuthEmail();
		this.joinDate = member.getCreatedDate();
		this.pnu = null;
		this.myFarm = null;
		this.notificationConditions = member.getNotificationConditions().stream()
			.filter(NotificationCondition::isActive)
			.map(NotificationConditionResponseDto::new)
			.toList();
	}
}
