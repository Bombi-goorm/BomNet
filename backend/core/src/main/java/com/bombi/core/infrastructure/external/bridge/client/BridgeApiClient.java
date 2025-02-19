package com.bombi.core.infrastructure.external.bridge.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bombi.core.infrastructure.external.bridge.dto.BridgeRecommendProductResponseDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BridgeApiClient {

	private final RestTemplate restTemplate;

	public BridgeRecommendProductResponseDto callRecommendProduct(String sidoCode) {
		return null;
	}
}
