package com.bombi.core.application.service.price;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.ChartDtoConverter;
import com.bombi.core.common.utils.time.TimePolicy;
import com.bombi.core.infrastructure.external.price.variety.client.AnnualVarietyPriceCollector;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

@Service
public class AnnualItemPriceService {

	private final TimePolicy timePolicy;
	private final AnnualVarietyPriceCollector annualVarietyPriceCollector;

	public AnnualItemPriceService(@Qualifier("annualPriceTimePolicy") TimePolicy timePolicy,
		AnnualVarietyPriceCollector annualVarietyPriceCollector) {
		this.timePolicy = timePolicy;
		this.annualVarietyPriceCollector = annualVarietyPriceCollector;
	}

	public List<ProductPriceDto> getAnnualItemPrice(String item) {
		String startDate = timePolicy.getStartTime();
		String endDate = timePolicy.getEndTime();

		List<VarietyPriceInfo> varietyPriceInfos = annualVarietyPriceCollector.sendVarietyPriceTrend(item, startDate, endDate);

		return ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);
	}

}
