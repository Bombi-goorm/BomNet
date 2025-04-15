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
}
