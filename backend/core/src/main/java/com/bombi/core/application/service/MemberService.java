package com.bombi.core.application.service;

import java.util.UUID;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.model.Role;
import com.bombi.core.domain.member.repository.MemberRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.member.repository.RoleRepository;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.domain.region.repository.RegionWeatherRepository;
import com.bombi.core.infrastructure.external.bigquery.client.BigQueryRecommendProductApiClient;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductRequestDto;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductResponseDto;
import com.bombi.core.infrastructure.external.soil.client.SoilCharacterApiClient;
import com.bombi.core.infrastructure.external.soil.client.SoilChemicalApiClient;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;
import com.bombi.core.presentation.dto.member.PnuRegisterRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final RegionWeatherRepository regionWeatherRepository;
	private final SoilCharacterApiClient soilCharacterApiClient;
	private final SoilChemicalApiClient soilChemicalApiClient;
	private final BigQueryRecommendProductApiClient bigQueryRecommendProductApiClient;

	@Transactional(readOnly = true)
	public MemberInfoResponseDto findMemberInfo(String memberId) {
		Member member = memberRepository.findMemberAndInfoById(UUID.fromString(memberId))
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		MemberInfo memberInfo = member.getMemberInfo();

		String pnuCode = memberInfo.getPnu();

		if (pnuCode == null || pnuCode.length() < 5) {
			return new MemberInfoResponseDto(member);
		}

		// 평균 기온, 평균 강수량
		String sidoCode = pnuCode.substring(0, 5);
		RegionWeather regionWeather = regionWeatherRepository.findWeatherInfoByPNU(sidoCode)
			.orElseThrow(() -> new IllegalArgumentException("회원 지역 정보를 찾을 수 없습니다."));

		// 토양 정보 api 호출
		SoilCharacterResponseDto soilCharacterResponse = soilCharacterApiClient.sendSoilCharacter(pnuCode);
		SoilChemicalResponseDto soilChemicalResponse = soilChemicalApiClient.sendSoilChemical(pnuCode);

		return new MemberInfoResponseDto(member, regionWeather, soilCharacterResponse, soilChemicalResponse, null);
	}

	@Transactional
	public void registerPnu(PnuRegisterRequestDto requestDto, String memberId) {
		Member member = memberRepository.findMemberAndInfoById(UUID.fromString(memberId))
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		Role roleFarmer = roleRepository.findByRoleName("ROLE_FARMER")
			.orElseThrow(() -> new IllegalArgumentException("ROLE_FARMER를 찾을 수 없다."));
		member.updatePnu(requestDto.getPnu(), roleFarmer);
	}
}
