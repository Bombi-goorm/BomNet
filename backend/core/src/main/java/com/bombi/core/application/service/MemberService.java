package com.bombi.core.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.member.repository.MemberInfoRepository;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.domain.region.repository.RegionWeatherRepository;
import com.bombi.core.infrastructure.external.bigquery.client.BigQueryRecommendProductApiClient;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductRequestDto;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductResponseDto;
import com.bombi.core.infrastructure.external.weather.client.EnvWeatherInfoApiClient;
import com.bombi.core.infrastructure.external.weather.dto.EnvWeatherResponseDto;
import com.bombi.core.infrastructure.external.soil.client.SoilCharacterApiClient;
import com.bombi.core.infrastructure.external.soil.client.SoilChemicalApiClient;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberInfoRepository memberInfoRepository;
	private final RegionWeatherRepository regionWeatherRepository;
	private final EnvWeatherInfoApiClient envWeatherInfoApiClient;
	private final SoilCharacterApiClient soilCharacterApiClient;
	private final SoilChemicalApiClient soilChemicalApiClient;
	private final BigQueryRecommendProductApiClient bigQueryRecommendProductApiClient;

	@Transactional(readOnly = true)
	public MemberInfoResponseDto findMemberInfo(String memberId) {
		UUID memberIdUUID = UUID.fromString(memberId);
		MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMemberId(memberIdUUID);

		String sidoCode = findSidoCodeFrom(memberInfo);
		String pnuCode = memberInfo.getPnu();

		// 평균 기온, 평균 강수량
		RegionWeather regionWeather = regionWeatherRepository.findWeatherInfoByPNU(sidoCode)
			.orElseThrow(() -> new IllegalArgumentException("회원 지역 정보를 찾을 수 없습니다."));

		// 지점별 기후 정보 api
		String stationId = regionWeather.getRegion().getStationNumber();
		EnvWeatherResponseDto envWeatherResponseDto = envWeatherInfoApiClient.sendWeatherInfo(stationId);

		// 토양 정보 api 호출
		SoilCharacterResponseDto soilCharacterResponse = soilCharacterApiClient.sendSoilCharacter(pnuCode);
		SoilChemicalResponseDto soilChemicalResponse = soilChemicalApiClient.sendSoilChemical(pnuCode);

		// 토양 코드, 평균 기온, 평균 강수량을 포함해 BigQuery로 api 요청 필요
		BigQueryRecommendProductRequestDto requestDto = new BigQueryRecommendProductRequestDto(
			pnuCode, sidoCode, soilCharacterResponse, soilChemicalResponse);
		BigQueryRecommendProductResponseDto responseDto = bigQueryRecommendProductApiClient.callRecommendProduct(requestDto);

		return new MemberInfoResponseDto(memberInfo, regionWeather, responseDto);
	}

	private String findSidoCodeFrom(MemberInfo memberInfo) {
		String pnuCode = memberInfo.getPnu();
		return pnuCode.substring(0, 2);
	}
}
