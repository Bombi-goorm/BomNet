package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.ChartDtoConverter;
import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.price.variety.client.AnnualVarietyPriceCollector;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

@Service
public class AnnualItemPriceService {

	private final TimeStringProvider timeStringProvider;
	private final AnnualVarietyPriceCollector annualVarietyPriceCollector;

	public AnnualItemPriceService(@Qualifier("annualTimeStringProvider") TimeStringProvider timeStringProvider,
		AnnualVarietyPriceCollector annualVarietyPriceCollector) {
		this.timeStringProvider = timeStringProvider;
		this.annualVarietyPriceCollector = annualVarietyPriceCollector;
	}

	public List<ProductPriceDto> getAnnualItemPrice(String item) {
		String startDateString = timeStringProvider.getStartDateString();
		String endDateString = timeStringProvider.getEndDateString();

		List<VarietyPriceInfo> varietyPriceInfos = annualVarietyPriceCollector.sendVarietyPriceTrend(item, startDateString, endDateString);

		return ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);
	}

}
