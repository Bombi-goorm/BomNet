package com.bombi.core.fasttest.price.period;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.price.QualityItemPriceService;
import com.bombi.core.application.service.price.RealtimeItemPriceService;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PeriodItemPriceController {

	private final QualityItemPriceService qualityItemPriceService;
	private final RealtimeItemPriceService realtimeItemPriceService;

	@GetMapping("/price/variety")
	ResponseEntity<?> periodItemPriceVariety() {
		// List<QualityChartData> response = qualityItemPriceService.getQualityItemPrice("사과");
		List<ProductPriceDto> response = realtimeItemPriceService.getRealtimeItemPrice("사과");
		return ResponseEntity.ok(response);
	}
}
