package com.bombi.core.fasttest.price.period;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.QualityItemPriceService;
import com.bombi.core.application.service.RealtimeItemPriceService;
import com.bombi.core.application.service.RegionItemPriceService;
import com.bombi.core.presentation.dto.price.ProductPriceDto;
import com.bombi.core.presentation.dto.price.QualityVarietyPriceData;
import com.bombi.core.presentation.dto.price.RegionChartData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PeriodItemPriceController {

	// private final DailyVarietyPriceCollector client;
	// private final MonthlyVarietyPriceCollector collector;
	private final RealtimeItemPriceService realtimeItemPriceService;
	private final QualityItemPriceService qualityItemPriceService;
	private final RegionItemPriceService regionItemPriceService;

	@GetMapping("/price/variety")
	ResponseEntity<?> periodItemPriceVariety() {
		// List<VarietyPriceInfo> response = client.sendVarietyPriceTrend("사과", "2025-02-19", "2025-03-18");
		//
		// List<ProductPriceDto> productPriceDtos = new ArrayList<>();
		// for (int i = 0; i < response.size(); i++) {
		// 	VarietyPriceInfo varietyPriceInfo = response.get(i);
		// 	ProductPriceDto productPriceDto = new ProductPriceDto(i, varietyPriceInfo.getVariety(),
		// 		varietyPriceInfo.getAveragePricePerKg(),
		// 		varietyPriceInfo.getDateTime());
		//
		// 	productPriceDtos.add(productPriceDto);
		// }
		// List<VarietyPriceInfo> response = collector.sendVarietyPriceTrend("사과", "2024-04-01", "2025-03-31");

		List<ProductPriceDto> response = realtimeItemPriceService.getRealtimeItemPrice("사과");
		// List<QualityVarietyPriceData> response = qualityItemPriceService.getQualityItemPrice("사과");
		// List<RegionChartData> response = regionItemPriceService.getRegionItemPrice("사과");

		return ResponseEntity.ok(response);
	}
}
