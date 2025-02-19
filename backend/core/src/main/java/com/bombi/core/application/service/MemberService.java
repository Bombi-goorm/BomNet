package com.bombi.core.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.member.model.MemberRole;
import com.bombi.core.domain.member.repository.MemberInfoRepository;
import com.bombi.core.domain.region.model.RegionWeather;
import com.bombi.core.domain.region.repository.RegionWeatherRepository;
import com.bombi.core.infrastructure.external.bridge.client.BridgeApiClient;
import com.bombi.core.infrastructure.external.bridge.dto.BridgeRecommendProductResponseDto;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberInfoRepository memberInfoRepository;
	private final RegionWeatherRepository regionWeatherRepository;
	private final BridgeApiClient bridgeApiClient;

	@Transactional(readOnly = true)
	public MemberInfoResponseDto findMemberInfo(String memberId) {
		UUID memberIdUUID = UUID.fromString(memberId);
		MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMemberId(memberIdUUID);
        Member member = memberInfo.getMember();

        // avgTemp, annualPrecipitation -> regionWeather table
        // soilType -> api or table
        RegionWeather regionWeather = null;
		BridgeRecommendProductResponseDto responseDto = null;
        if (MemberRole.isFarmer(member)) {
            String sidoCode = findSidoCodeFrom(memberInfo);
            regionWeather = regionWeatherRepository.findWeatherInfoByPNU(sidoCode)
                .orElseThrow(() -> new IllegalArgumentException("회원 지역 정보를 찾을 수 없습니다."));

            responseDto = bridgeApiClient.callRecommendProduct(sidoCode);
        }

		return new MemberInfoResponseDto(memberInfo, regionWeather, responseDto);
	}

	private String findSidoCodeFrom(MemberInfo memberInfo) {
		String pnuCode = memberInfo.getPnu();
		return pnuCode.substring(0, 2);
	}
}
