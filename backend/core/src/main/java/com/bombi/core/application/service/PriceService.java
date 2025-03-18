package com.bombi.core.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.presentation.dto.price.ProductPriceDto;
import com.bombi.core.presentation.dto.price.OverallPriceInfoResponse;
import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {

	private final AnnualItemPriceService annualItemPriceService;
	private final MonthlyItemPriceService monthlyItemPriceService;
	private final DailyItemPriceService dailyItemPriceService;
	private final SankeyChartPriceService sankeyChartPriceService;

	/**
	 * 검색한 품목의 품종별 가격 추이
	 * @param item : 품목. ex) 사과
	 */
	public OverallPriceInfoResponse findItemFlow(String item, String date) {
		//연간 가격 정보
		List<ProductPriceDto> annualItemPrice = annualItemPriceService.getAnnualItemPrice(item);

		//월간 가격 정보
		List<ProductPriceDto> monthlyItemPrice = monthlyItemPriceService.getMonthlyItemPrice(item);

		//일간 가격 정보
		List<ProductPriceDto> dailyItemPrice = dailyItemPriceService.getDailyItemPrice(item);

		//실시간 가격 정보

		//상태(특상, 상, 중, 하) 별 가격 정보

		//지역별 가격 정보

		// 품종별 sankey chart
		SankeyDataResponseDto sankeyChartInfo = sankeyChartPriceService.findSankeyChartInfo(item, date);
		return new OverallPriceInfoResponse(annualItemPrice, monthlyItemPrice, dailyItemPrice, sankeyChartInfo);
	}


}
