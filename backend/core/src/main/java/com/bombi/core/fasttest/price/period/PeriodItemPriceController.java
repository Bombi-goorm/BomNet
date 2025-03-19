package com.bombi.core.fasttest.price.period;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.QualityItemPriceService;
import com.bombi.core.presentation.dto.price.QualityChartData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PeriodItemPriceController {

	private final QualityItemPriceService qualityItemPriceService;

	@GetMapping("/price/variety")
	ResponseEntity<?> periodItemPriceVariety() {
		List<QualityChartData> response = qualityItemPriceService.getQualityItemPrice("사과");

		return ResponseEntity.ok(response);
	}
}
