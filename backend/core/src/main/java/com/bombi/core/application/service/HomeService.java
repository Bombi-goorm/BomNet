package com.bombi.core.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.model.MemberInfo;
import com.bombi.core.domain.member.repository.MemberInfoRepository;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.gcs.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.gcs.client.WeatherForecastApiClient;
import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReportResponse;
import com.bombi.core.infrastructure.external.gcs.dto.WeatherForecastResponse;
import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.presentation.dto.home.HomeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {

	private final MemberInfoRepository memberInfoRepository;
	private final RegionRepository regionRepository;
	private final SpecialWeatherReportApiClient specialWeatherReportApiClient;
	private final WeatherForecastApiClient weatherForecastApiClient;
	private final NaverNewsApiClient naverNewsApiClient;

	@Transactional(readOnly = true)
	public HomeResponseDto homeInfo(String memberId) {
		MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMemberId(UUID.fromString(memberId));

		String sidoCode = memberInfo.getPnu().substring(0, 2);
		Region region = regionRepository.findByWeatherSiDoCode(sidoCode)
			.orElseThrow(() -> new IllegalArgumentException("sido코드에 해당하는 지역정보를 찾을 수 없습니다."));


		// 인기/관심 상품 리스트 조회 :: BestProductApiClient
		// 상품 가격 추이


		// 기상특보 :: SpecialWeatherReportApiClient
		SpecialWeatherReportResponse specialWeatherReportResponse = specialWeatherReportApiClient.sendSpecialWeatherReport();

		// 기상 예보 :: WeatherForecastApiClient
		// 날씨 정보
		WeatherForecastResponse weatherForecastResponse = weatherForecastApiClient.sendWeatherForecast();

		// 뉴스 : NaverNewApiClient
		// NaverNewsResponse naverNewsResponse = naverNewsApiClient.sendNews();
		NaverNewsResponse naverNewsResponse = naverNewsApiClient.sendNews();

		return new HomeResponseDto(specialWeatherReportResponse, weatherForecastResponse, naverNewsResponse);
	}

}
