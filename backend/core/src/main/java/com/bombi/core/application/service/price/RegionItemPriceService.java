package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.price.variety.client.RegionVarietyPriceCollector;
import com.bombi.core.presentation.dto.price.RegionChartData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionItemPriceService {

	private final RegionVarietyPriceCollector regionVarietyPriceCollector;

	public List<RegionChartData> getRegionItemPrice(String item) {
		return regionVarietyPriceCollector.sendVarietyPriceTrend(item);
	}

	// private String createStartDate() {
	// 	LocalDateTime localDateTime = LocalDateTime.now().minusDays(7);
	// 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	// 	return localDateTime.format(formatter);
	// }
	//
	// private String createEndDate() {
	// 	LocalDateTime localDateTime = LocalDateTime.now();
	// 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	// 	return localDateTime.format(formatter);
	// }
}
