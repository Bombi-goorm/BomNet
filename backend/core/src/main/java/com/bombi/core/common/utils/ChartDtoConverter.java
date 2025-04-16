package com.bombi.core.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

public class ChartDtoConverter {

	public static List<ProductPriceDto> convertToProductPriceDto(List<VarietyPriceInfo> varietyPriceInfos) {
		List<ProductPriceDto> productPriceDtos = new ArrayList<>();

		for (int index = 0; index < varietyPriceInfos.size(); index++) {
			VarietyPriceInfo varietyPriceInfo = varietyPriceInfos.get(index);

			int chartIndex = index + 1;
			ProductPriceDto productPriceDto = ProductPriceDto.of(chartIndex, varietyPriceInfo);

			productPriceDtos.add(productPriceDto);
		}

		return productPriceDtos;
	}
}
