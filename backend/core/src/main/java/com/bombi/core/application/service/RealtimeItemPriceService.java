package com.bombi.core.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RealtimeItemPriceService {
	private final RealtimeVarietyPriceCollector realtimeVarietyPriceCollector;

	public List<ProductPriceDto> getRealtimeItemPrice(String item) {
		String startDateTime = createStartDate();
		String endDateTime = createEndDate();

		List<VarietyPriceInfo> varietyPriceInfos = realtimeVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);
		return convertToProductPriceDto(varietyPriceInfos);
	}

	private String createStartDate() {
		LocalDateTime localDateTime = LocalDateTime.now().minusHours(24);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	private String createEndDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	private List<ProductPriceDto> convertToProductPriceDto(List<VarietyPriceInfo> varietyPriceInfos) {
		List<ProductPriceDto> productPriceDtos = new ArrayList<>();

		for (int index = 0; index < varietyPriceInfos.size(); index++) {
			VarietyPriceInfo varietyPriceInfo = varietyPriceInfos.get(index);

			int chartIndex = index + 1;
			ProductPriceDto productPriceDto = new ProductPriceDto(chartIndex, varietyPriceInfo.getVariety(),
				varietyPriceInfo.getAveragePricePerKg(),
				varietyPriceInfo.getDateTime());

			productPriceDtos.add(productPriceDto);
		}

		return productPriceDtos;
	}
}
