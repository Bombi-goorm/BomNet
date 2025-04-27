package com.bombi.core.application.service.price;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.price.variety.client.AnnualVarietyPriceCollector;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

@ExtendWith(MockitoExtension.class)
public class AnnualItemPriceServiceTest {

	@InjectMocks
	private AnnualItemPriceService annualItemPriceService;

	@Mock
	private TimeStringProvider timeStringProvider;

	@Mock
	private AnnualVarietyPriceCollector annualVarietyPriceCollector;

	@DisplayName("년도별 가격 데이터 조회 흐름 테스트")
	@ParameterizedTest
	@MethodSource("getVarietyPriceInfo")
	void annualPriceFlowTest(List<VarietyPriceInfo> varietyPriceInfos) {
		//given
		String item = "사과";
		String startDateString = "2024-04-26";
		String endDateString = "2025-04-26";

		when(timeStringProvider.getStartDateString()).thenReturn(startDateString);
		when(timeStringProvider.getEndDateString()).thenReturn(endDateString);
		when(annualVarietyPriceCollector.sendVarietyPriceTrend(eq(item), eq(startDateString), eq(endDateString)))
			.thenReturn(varietyPriceInfos);

		//when
		List<ProductPriceDto> productPriceDtos = annualItemPriceService.getAnnualItemPrice(item);

		//then
		verify(timeStringProvider).getStartDateString();
		verify(timeStringProvider).getEndDateString();
		verify(annualVarietyPriceCollector).sendVarietyPriceTrend(item, startDateString, endDateString);

		assertThat(productPriceDtos).hasSize(varietyPriceInfos.size());
	}

	public static Stream<List<VarietyPriceInfo>> getVarietyPriceInfo() {
		return Stream.of(
			List.of(
				new VarietyPriceInfo("홍옥", "2024-05-01", 10000L),
				new VarietyPriceInfo("홍옥", "2024-06-01", 12000L)
			),
			List.of()
		);
	}


}
