package com.bombi.core.presentation.dto.home;

import java.util.List;

import com.bombi.core.infrastructure.external.weather.dto.SpecialWeatherReportResponse;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.presentation.dto.weather.WeatherNoticeResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeResponseDto {

	private BestItem bestItems; // 인기/관심 상품
	private List<WeatherNoticeResponseDto> weatherNotice; //  기상 특보
	private WeatherExpection weatherExpection; // 기상 예보
	private List<NewsResponseDto> news; // 뉴스

	public HomeResponseDto(
		List<ProductPriceResponse> productPriceResponses,
		SpecialWeatherReportResponse specialWeatherReport,
		WeatherExpection weatherForecastResponse,
		NaverNewsResponse naverNewsResponse) {
		this.bestItems = new BestItem(productPriceResponses);
		this.weatherNotice = specialWeatherReport.getItem().stream()
			.map(WeatherNoticeResponseDto::new)
			.toList();
		this.weatherExpection = weatherForecastResponse;
		this.news = naverNewsResponse.getItems()
			.stream()
			.map(NewsResponseDto::new)
			.toList();
	}
}
