package com.bombi.core.application.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MonthlyItemPriceService {

	private final MonthlyVarietyPriceCollector monthlyVarietyPriceCollector;

	public List<ProductPriceDto> getMonthlyItemPrice(String item) {
		String startDate = createStartDate();
		String endDate = createEndDate();

		List<VarietyPriceInfo> varietyPriceInfos = monthlyVarietyPriceCollector.sendVarietyPriceTrend(item, startDate, endDate);

		return convertToProductPriceDto(varietyPriceInfos);
	}

	private String createStartDate() {
		LocalDate localDate = LocalDate.now().minusMonths(12);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDate.format(formatter);
	}

	private String createEndDate() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDate.format(formatter);
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
