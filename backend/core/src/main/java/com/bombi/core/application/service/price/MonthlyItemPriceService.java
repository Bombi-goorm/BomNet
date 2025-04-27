package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.ChartDtoConverter;
import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.price.variety.client.MonthlyVarietyPriceCollector;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

@Service
public class MonthlyItemPriceService {

	private final TimeStringProvider timeStringProvider;
	private final MonthlyVarietyPriceCollector monthlyVarietyPriceCollector;

	public MonthlyItemPriceService(@Qualifier("monthlyTimeStringProvider") TimeStringProvider timeStringProvider,
		MonthlyVarietyPriceCollector monthlyVarietyPriceCollector) {
		this.timeStringProvider = timeStringProvider;
		this.monthlyVarietyPriceCollector = monthlyVarietyPriceCollector;
	}

	public List<ProductPriceDto> getMonthlyItemPrice(String item) {
		String startDateString = timeStringProvider.getStartDateString();
		String endDateString = timeStringProvider.getEndDateString();

		List<VarietyPriceInfo> varietyPriceInfos = monthlyVarietyPriceCollector.sendVarietyPriceTrend(item, startDateString, endDateString);

		return ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);
	}

}
