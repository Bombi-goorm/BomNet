package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.time.TimePolicy;
import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.price.variety.client.QualityVarietyPriceCollector;
import com.bombi.core.presentation.dto.price.QualityChartData;

@Service
public class QualityItemPriceService {

	private final TimeStringProvider timeStringProvider;
	private final QualityVarietyPriceCollector qualityVarietyPriceCollector;

	public QualityItemPriceService(@Qualifier("dailyTimeStringProvider") TimeStringProvider timeStringProvider,
		QualityVarietyPriceCollector qualityVarietyPriceCollector) {
		this.timeStringProvider = timeStringProvider;
		this.qualityVarietyPriceCollector = qualityVarietyPriceCollector;
	}

	public List<QualityChartData> getQualityItemPrice(String item) {
		String startDateTime = timeStringProvider.getStartDateString();
		String endDateTime = timeStringProvider.getEndDateString();

		return qualityVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);
	}

}
