package com.bombi.core.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bombi.core.presentation.dto.price.QualityVarietyPriceData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QualityItemPriceService {

	private final QualityVarietyPriceCollector qualityVarietyPriceCollector;

	public List<QualityVarietyPriceData> getQualityItemPrice(String item) {
		String startDateTime = createStartDate();
		String endDateTime = createEndDate();

		List<QualityVarietyPriceData> data = qualityVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);

		return data;
	}

	private String createStartDate() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(7);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter);
	}

	private String createEndDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDateTime.format(formatter);
	}

}
