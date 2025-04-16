package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.time.TimePolicy;
import com.bombi.core.infrastructure.external.price.variety.client.QualityVarietyPriceCollector;
import com.bombi.core.presentation.dto.price.QualityChartData;

@Service
public class QualityItemPriceService {

	private final TimePolicy timePolicy;
	private final QualityVarietyPriceCollector qualityVarietyPriceCollector;

	public QualityItemPriceService(@Qualifier("dailyPriceTimePolicy") TimePolicy timePolicy,
		QualityVarietyPriceCollector qualityVarietyPriceCollector) {
		this.timePolicy = timePolicy;
		this.qualityVarietyPriceCollector = qualityVarietyPriceCollector;
	}

	public List<QualityChartData> getQualityItemPrice(String item) {
		String startDateTime = timePolicy.getStartTime();
		String endDateTime = timePolicy.getEndTime();

		return qualityVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);
	}

}
