package com.bombi.core.common.utils;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

public class ChartDtoConverterTest {

	@DisplayName("변환 테스트")
	@Test
	void convert() {
		//given
		List<VarietyPriceInfo> varietyPriceInfos = List.of(new VarietyPriceInfo("홍옥", "2024-05-01", 10000L),
			new VarietyPriceInfo("홍옥", "2024-06-01", 12000L));

		//when
		List<ProductPriceDto> productPriceDtos = ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);

		//then
		assertThat(productPriceDtos).hasSize(2);
		assertThat(productPriceDtos).extracting(ProductPriceDto::getVariety).containsExactly("홍옥", "홍옥");
		assertThat(productPriceDtos).extracting(ProductPriceDto::getDateTime).containsExactly("2024-05-01", "2024-06-01");
		assertThat(productPriceDtos).extracting(ProductPriceDto::getPrice).containsExactly(10000L, 12000L);
	}
}
