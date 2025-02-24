package com.bombi.core.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.infrastructure.external.bigquery.client.BestProductPriceApiClient;
import com.bombi.core.infrastructure.external.bigquery.dto.BestProductPriceResponse;
import com.bombi.core.infrastructure.external.gcs.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.gcs.client.WeatherForecastApiClient;
import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReportResponse;
import com.bombi.core.infrastructure.external.gcs.dto.WeatherForecastResponse;
import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.presentation.dto.home.HomeResponseDto;
import com.bombi.core.presentation.dto.home.ProductPriceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {

	// private final MemberInfoRepository memberInfoRepository;
	// private final RegionRepository regionRepository;

	private final ProductRepository productRepository;
	private final BestProductPriceApiClient bestProductApiClient;
	private final SpecialWeatherReportApiClient specialWeatherReportApiClient;
	private final WeatherForecastApiClient weatherForecastApiClient;
	private final NaverNewsApiClient naverNewsApiClient;

	@Transactional(readOnly = true)
	public HomeResponseDto homeInfo() {
		// MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMemberId(UUID.fromString(memberId));
		//
		// String sidoCode = memberInfo.getPnu().substring(0, 2);
		// Region region = regionRepository.findByWeatherSiDoCode(sidoCode)
		// 	.orElseThrow(() -> new IllegalArgumentException("sido코드에 해당하는 지역정보를 찾을 수 없습니다."));

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
		// WeatherForecastResponse weatherForecastResponse = weatherForecastApiClient.sendWeatherForecast();

		// 농산물 뉴스
		NaverNewsResponse naverNewsResponse = naverNewsApiClient.sendNews();

		return new HomeResponseDto(productPriceResponses, specialWeatherReportResponse, null, naverNewsResponse);
	}

	private List<String> getProductCodes(List<Product> bestProducts) {
		return bestProducts.stream().map(Product::getCategory)
			.map(category -> {
				Category midCategory = category.getParent();
				Category bigCategory = midCategory.getParent();
				return bigCategory.getCode() + midCategory.getCode() + category.getCode();
			})
			.toList();
	}

}
