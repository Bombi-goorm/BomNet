package com.bombi.core.application.service.home;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bombi.core.application.service.news.NewsService;
import com.bombi.core.application.service.price.BestProductPriceService;
import com.bombi.core.application.service.region.RegionService;
import com.bombi.core.application.service.weather.forecast.WeatherForecastService;
import com.bombi.core.application.service.weather.special.SpecialWeatherReportService;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.infrastructure.external.bigquery.client.BestProductPriceApiClient;
import com.bombi.core.infrastructure.external.weather.client.SpecialWeatherReportApiClient;
import com.bombi.core.infrastructure.external.weather.client.WeatherForecastApiClient;
import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;
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

	private final RegionService regionService;
	private final BestProductPriceService bestProductPriceService;
	private final SpecialWeatherReportService specialWeatherReportService;
	private final WeatherForecastService weatherForecastService;
	private final NewsService newsService;

	@Transactional(readOnly = true)
	public HomeResponseDto homeInfo(HomeRequestDto requestDto) {
		Region region = regionService.findRegionInfo(requestDto);

		// 인기/관심 상품 가격 추이 :: BestProductApiClient -> bigquery
		List<ProductPriceResponse> productPriceResponses = bestProductPriceService.getBestProductPrice();

		// 기상 특보
		SpecialWeatherReportResponse specialWeatherReportResponse = specialWeatherReportService.getSpecialWeatherReport();

		// 기상 예보 -> bigquery
		WeatherExpection weatherExpection = weatherForecastService.getWeatherForecast(region);

		// 농산물 뉴스
		NaverNewsResponse naverNewsResponse = newsService.getNews();

		return new HomeResponseDto(productPriceResponses, specialWeatherReportResponse, weatherExpection, naverNewsResponse);
	}

}
