package com.bombi.core.infrastructure.external.bigquery.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductRequestDto;
import com.bombi.core.infrastructure.external.bigquery.dto.BigQueryRecommendProductResponseDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BigQueryRecommendProductApiClient {

	private final RestTemplate restTemplate;

	public BigQueryRecommendProductResponseDto callRecommendProduct(BigQueryRecommendProductRequestDto request) {


		return null;
	}
}
