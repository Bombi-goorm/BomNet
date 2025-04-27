package com.bombi.core.application.service.price;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bombi.core.common.utils.ChartDtoConverter;
import com.bombi.core.common.utils.time.provider.TimeStringProvider;
import com.bombi.core.infrastructure.external.price.variety.client.RealtimeVarietyPriceCollector;
import com.bombi.core.application.service.cache.RedisCacheService;
import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RealtimeItemPriceService {

	public static final Duration CACHE_DURATION = Duration.ofMinutes(30L);
	public static final Duration FALLBACK_CACHE_DURATION = Duration.ofHours(24L);

	private final TimeStringProvider timeStringProvider;
	private final RealtimeVarietyPriceCollector realtimeVarietyPriceCollector;
	private final RedisCacheService redisCacheService;

	public RealtimeItemPriceService(@Qualifier("realtimeStringProvider") TimeStringProvider timeStringProvider,
		RealtimeVarietyPriceCollector realtimeVarietyPriceCollector,
		RedisCacheService redisCacheService) {
		this.timeStringProvider = timeStringProvider;
		this.realtimeVarietyPriceCollector = realtimeVarietyPriceCollector;
		this.redisCacheService = redisCacheService;
	}

	public List<ProductPriceDto> getRealtimeItemPrice(String item) {
		String startDateTime = timeStringProvider.getStartDateString();
		String endDateTime = timeStringProvider.getEndDateString();

		List<VarietyPriceInfo> varietyPriceInfos = realtimeVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);

		// 캐시 조회 후 실제 값 조회에도 데이터가 없다면 fallback 캐시 반환
		if (varietyPriceInfos == null || varietyPriceInfos.isEmpty()) {
			List<VarietyPriceInfo> fallbackVarietyPriceInfos = redisCacheService.getFromCache("RealTimePrice::" + item + "::fallback");

			if(fallbackVarietyPriceInfos == null || fallbackVarietyPriceInfos.isEmpty()) {
				log.info("Fallback 캐시값도 비어있음. 기본값 반환");
				return Collections.emptyList();
			}

			// fallback 캐시 값 expire 추가
			redisCacheService.extendTTL("RealTimePrice::" + item + "::fallback");

			// 기존 캐시에 fallback캐시값을 복사해 저장
			redisCacheService.setCacheWithTime("RealTimePrice::" + item, fallbackVarietyPriceInfos, CACHE_DURATION);

			return ChartDtoConverter.convertToProductPriceDto(fallbackVarietyPriceInfos);
		}

		// 캐시 or 실제 값 조회에 데이터가 존재한다면 fallback 캐시 설정
		log.info("Fallback 캐시 업데이트");
		redisCacheService.setCacheWithTime("RealTimePrice::" + item + "::fallback", varietyPriceInfos, FALLBACK_CACHE_DURATION);

		return ChartDtoConverter.convertToProductPriceDto(varietyPriceInfos);
	}

}
