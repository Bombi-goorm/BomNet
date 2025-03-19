package com.bombi.core.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.presentation.dto.price.QualityChartData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QualityItemPriceService {

	private final QualityVarietyPriceCollector qualityVarietyPriceCollector;

	public List<QualityChartData> getQualityItemPrice(String item) {
		String startDateTime = createStartDate();
		String endDateTime = createEndDate();

		return qualityVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);
	}

	private String createStartDate() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(30);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter);
	}

	private String createEndDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter);
	}

}
