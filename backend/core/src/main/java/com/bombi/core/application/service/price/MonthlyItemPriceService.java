package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.ChartDtoConverter;
import com.bombi.core.common.utils.time.TimePolicy;
import com.bombi.core.infrastructure.external.price.variety.client.MonthlyVarietyPriceCollector;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

@Service
public class MonthlyItemPriceService {

	private final TimePolicy timePolicy;
	private final MonthlyVarietyPriceCollector monthlyVarietyPriceCollector;

	public MonthlyItemPriceService(@Qualifier("monthlyPriceTimePolicy") TimePolicy timePolicy,
		MonthlyVarietyPriceCollector monthlyVarietyPriceCollector) {
		this.timePolicy = timePolicy;
		this.monthlyVarietyPriceCollector = monthlyVarietyPriceCollector;
	}

	public List<ProductPriceDto> getMonthlyItemPrice(String item) {
		String startDate = timePolicy.getStartTime();
		String endDate = timePolicy.getEndTime();

		List<VarietyPriceInfo> varietyPriceInfos = monthlyVarietyPriceCollector.sendVarietyPriceTrend(item, startDate, endDate);

		return ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);
	}

}
