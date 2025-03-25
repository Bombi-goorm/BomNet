package com.bombi.core.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.presentation.dto.price.PriceSearchRequestDto;
import com.bombi.core.presentation.dto.price.ProductPriceDto;
import com.bombi.core.presentation.dto.price.OverallPriceInfoResponse;
import com.bombi.core.presentation.dto.price.QualityChartData;
import com.bombi.core.presentation.dto.price.RegionChartData;
import com.bombi.core.presentation.dto.price.chart.SankeyDataResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceService {

	private final AnnualItemPriceService annualItemPriceService;
	private final MonthlyItemPriceService monthlyItemPriceService;
	private final DailyItemPriceService dailyItemPriceService;
	private final RealtimeItemPriceService realtimeItemPriceService;
	private final QualityItemPriceService qualityItemPriceService;
	private final RegionItemPriceService regionItemPriceService;
	private final SankeyChartPriceService sankeyChartPriceService;

	/**
	 * 검색한 품목의 품종별 가격 추이
	 */
	public OverallPriceInfoResponse findItemFlow(PriceSearchRequestDto requestDto) {
		String item = requestDto.getItem();

		//연간 가격 정보
		List<ProductPriceDto> annualItemPrice = annualItemPriceService.getAnnualItemPrice(item);

		//월간 가격 정보
		List<ProductPriceDto> monthlyItemPrice = monthlyItemPriceService.getMonthlyItemPrice(item);

		//일간 가격 정보
		List<ProductPriceDto> dailyItemPrice = dailyItemPriceService.getDailyItemPrice(item);

		//실시간 가격 정보
		List<ProductPriceDto> realTimeItemPrice = realtimeItemPriceService.getRealtimeItemPrice(item);

		//상태(특상, 상, 중, 하) 별 가격 정보
		List<QualityChartData> qualityChartData = qualityItemPriceService.getQualityItemPrice(item);

		//지역별 가격 정보
		List<RegionChartData> regionItemPrice = regionItemPriceService.getRegionItemPrice(item);

		// 품종별 sankey chart
		String date = LocalDate.now().toString();
		SankeyDataResponseDto sankeyChartInfo = sankeyChartPriceService.findSankeyChartInfo(item, date);

		return new OverallPriceInfoResponse(annualItemPrice, monthlyItemPrice, dailyItemPrice, realTimeItemPrice, qualityChartData, regionItemPrice, sankeyChartInfo);
	}


}
