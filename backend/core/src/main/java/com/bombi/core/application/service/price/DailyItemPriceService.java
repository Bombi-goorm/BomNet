package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.ChartDtoConverter;
import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.price.variety.client.DailyVarietyPriceCollector;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

@Service
public class DailyItemPriceService {

	private final TimeStringProvider timeStringProvider;
	private final DailyVarietyPriceCollector dailyVarietyPriceCollector;

	public DailyItemPriceService(@Qualifier("dailyTimeStringProvider") TimeStringProvider timeStringProvider,
		DailyVarietyPriceCollector dailyVarietyPriceCollector) {
		this.timeStringProvider = timeStringProvider;
		this.dailyVarietyPriceCollector = dailyVarietyPriceCollector;
	}

	public List<ProductPriceDto> getDailyItemPrice(String item) {
		String startDateString = timeStringProvider.getStartDateString();
		String endDateString = timeStringProvider.getEndDateString();

		List<VarietyPriceInfo> varietyPriceInfos = dailyVarietyPriceCollector.sendVarietyPriceTrend(item, startDateString, endDateString);

		return ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);
	}

}
