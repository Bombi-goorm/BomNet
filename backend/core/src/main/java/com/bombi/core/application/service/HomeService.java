package com.bombi.core.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
import com.bombi.core.presentation.dto.home.HomeRequestDto;
import com.bombi.core.presentation.dto.home.HomeResponseDto;
import com.bombi.core.presentation.dto.home.ProductPriceResponse;
import com.bombi.core.presentation.dto.home.WeatherExpection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {

	private final RegionRepository regionRepository;
	private final ProductRepository productRepository;
	private final BestProductPriceApiClient bestProductApiClient;
	private final SpecialWeatherReportApiClient specialWeatherReportApiClient;
	private final WeatherForecastApiClient weatherForecastApiClient;
	private final NaverNewsApiClient naverNewsApiClient;

	@Transactional(readOnly = true)
	public HomeResponseDto homeInfo(HomeRequestDto requestDto) {
		Region region = findRegionInfo(requestDto);

		// 인기/관심 상품 가격 추이 :: BestProductApiClient -> bigquery
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id"));
		List<Product> bestProducts = productRepository.findTop5ByCategoryId(pageRequest);
		List<String> midCategoryNames = bestProducts.stream()
			.map(product -> product.getCategory().getParent().getName())
			.collect(Collectors.toList());

		List<ProductPriceResponse> productPriceResponses = bestProductApiClient.callBestProductPrice(midCategoryNames);

		// 기상 특보
		SpecialWeatherReportResponse specialWeatherReportResponse = specialWeatherReportApiClient.sendSpecialWeatherReport();

		// 기상 예보 -> bigquery
		WeatherExpection weatherExpection = weatherForecastApiClient.sendWeatherForecast(region);

		// 농산물 뉴스
		NaverNewsResponse naverNewsResponse = naverNewsApiClient.sendNews();

		return new HomeResponseDto(productPriceResponses, specialWeatherReportResponse, weatherExpection, naverNewsResponse);
	}

	private Region findRegionInfo(HomeRequestDto requestDto) {

		if(requestDto == null || !StringUtils.hasText(requestDto.getPnu())) {
			return regionRepository.findByStationName("서울")
				.orElseThrow(() -> new IllegalArgumentException("서울 지역 정보를 찾을 없습니다."));
		}

		String weatherSiGunGuCode = requestDto.getPnu().substring(0, 5);

		return regionRepository.findByWeatherSiGunGuCode(weatherSiGunGuCode)
			.orElseThrow(() -> new IllegalArgumentException("해당 시군구 코드를 가지는 지역 정보를 찾을 수 없습니다."));
	}

}
