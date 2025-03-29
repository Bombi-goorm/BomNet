package com.bombi.core.application.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.price.variety.dto.VarietyPriceInfo;
import com.bombi.core.presentation.dto.price.ProductPriceDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealtimeItemPriceService {
	private final RealtimeVarietyPriceCollector realtimeVarietyPriceCollector;
	private final RedisTemplate<String, List<VarietyPriceInfo>> redisTemplate;

	public List<ProductPriceDto> getRealtimeItemPrice(String item) {
		String startDateTime = createStartDate();
		String endDateTime = createEndDate();

		List<VarietyPriceInfo> varietyPriceInfos = realtimeVarietyPriceCollector.sendVarietyPriceTrend(item, startDateTime, endDateTime);

		// 캐시 조회 후 실제 값 조회에도 데이터가 없다면 fallback 캐시 반환
		if (varietyPriceInfos == null || varietyPriceInfos.isEmpty()) {
			List<VarietyPriceInfo> fallbackVarietyPriceInfos = redisTemplate.opsForValue().get("RealTimePrice::" + item + "::fallback");

			if(fallbackVarietyPriceInfos == null || fallbackVarietyPriceInfos.isEmpty()) {
				log.info("Fallback 캐시값도 비어있음. 기본값 반환");
				return Collections.emptyList();
			}

			// fallback 캐시 값 expire 추가
			Long currentTTL = redisTemplate.getExpire("RealTimePrice::" + item + "::fallback", TimeUnit.HOURS);
			long extendedTTL = (currentTTL != null ? currentTTL : 0) + TimeUnit.HOURS.toHours(1);
			redisTemplate.expire("RealTimePrice::" + item + "::fallback", extendedTTL, TimeUnit.HOURS);

			// 기존 캐시에 fallback캐시값을 복사해 저장
			redisTemplate.opsForValue().set("RealTimePrice::" + item, fallbackVarietyPriceInfos, Duration.ofMinutes(30L));

			return convertToProductPriceDto(fallbackVarietyPriceInfos);
		}

		// 캐시 or 실제 값 조회에 데이터가 존재한다면 fallback 캐시 설정
		redisTemplate.opsForValue().set("RealTimePrice::" + item + "::fallback", varietyPriceInfos, Duration.ofHours(24L));

		return convertToProductPriceDto(varietyPriceInfos);
	}

	private String createStartDate() {
		LocalDate localDate = LocalDate.now().minusDays(2L);
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDateTime localDateTime = LocalDateTime.of(localDate, midnight);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	private String createEndDate() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	private List<ProductPriceDto> convertToProductPriceDto(List<VarietyPriceInfo> varietyPriceInfos) {
		List<ProductPriceDto> productPriceDtos = new ArrayList<>();

		for (int index = 0; index < varietyPriceInfos.size(); index++) {
			VarietyPriceInfo varietyPriceInfo = varietyPriceInfos.get(index);

			int chartIndex = index + 1;
			ProductPriceDto productPriceDto = new ProductPriceDto(chartIndex, varietyPriceInfo.getVariety(),
				varietyPriceInfo.getAveragePricePerKg(),
				varietyPriceInfo.getDateTime(),
				varietyPriceInfo.getMarket());

			productPriceDtos.add(productPriceDto);
		}

		return productPriceDtos;
	}
}
