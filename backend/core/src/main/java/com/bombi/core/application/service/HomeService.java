package com.bombi.core.application.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.bigquery.client.BestProductPriceApiClient;
import com.bombi.core.infrastructure.external.gcs.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReportResponse;
import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.infrastructure.external.weather.dto.WeatherForecastResponse;
import com.bombi.core.presentation.dto.home.HomeResponseDto;
import com.bombi.core.presentation.dto.home.ProductPriceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {

	private final ProductRepository productRepository;
	private final BestProductPriceApiClient bestProductApiClient;
	private final SpecialWeatherReportApiClient specialWeatherReportApiClient;
	private final WeatherForecastApiClient weatherForecastApiClient;
	private final NaverNewsApiClient naverNewsApiClient;
	private final MemberRepository memberRepository;
	private final RegionRepository regionRepository;

	@Transactional(readOnly = true)
	public HomeResponseDto homeInfo() {
		//사용자 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Region region = findRegionInfo(authentication);

		// 인기/관심 상품 가격 추이 :: BestProductApiClient -> bigquery
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id"));
		List<Product> bestProducts = productRepository.findTop5ByCategoryId(pageRequest);
		List<String> midCategoryNames = bestProducts.stream()
			.map(product -> product.getCategory().getParent().getName())
			.collect(Collectors.toList());

		List<ProductPriceResponse> productPriceResponses = bestProductApiClient.callBestProductPrice(midCategoryNames);

		// 기상 특보
		SpecialWeatherReportResponse specialWeatherReportResponse = specialWeatherReportApiClient.sendSpecialWeatherReport("108");

		// 기상 예보 -> bigquery
		// WeatherForecastResponse weatherForecastResponse = weatherForecastApiClient.sendWeatherForecast(region.getXx(), region.getYy());
		WeatherForecastResponse weatherForecastResponse = weatherForecastApiClient.sendWeatherForecast("119", "61");

		// 농산물 뉴스
		NaverNewsResponse naverNewsResponse = naverNewsApiClient.sendNews();

		return new HomeResponseDto(productPriceResponses, specialWeatherReportResponse, weatherForecastResponse, naverNewsResponse);
	}

	private Region findRegionInfo(Authentication authentication) {
		if(authentication == null) {
			return regionRepository.findByStationName("서울")
				.orElseThrow(() -> new IllegalArgumentException("서울에 해당하는 지역 정보를 찾을 수 없습니다."));
		}

		Member member = memberRepository.findMemberAndInfoById(UUID.fromString(authentication.getName()))
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		String weatherSiGunGuCode = member.getSidoCode();

		Region region = regionRepository.findByWeatherSiGunGuCode(weatherSiGunGuCode)
			.orElseThrow(() -> new IllegalArgumentException("해당 시군구 코드를 가지는 지역 정보를 찾을 수 없습니다."));

		return region;
	}

}
