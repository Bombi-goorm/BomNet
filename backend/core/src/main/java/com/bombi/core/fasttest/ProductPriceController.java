package com.bombi.core.fasttest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.infrastructure.external.bigquery.client.BestProductPriceApiClient;
import com.bombi.core.infrastructure.external.bigquery.dto.BestProductPriceResponse;
import com.bombi.core.presentation.dto.home.ProductPriceResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductPriceController {

	private final BestProductPriceApiClient client;

	@GetMapping("/best/price")
	ResponseEntity<?> price() {
		List<ProductPriceResponse> responses = client.callBestProductPrice(List.of());
		return ResponseEntity.ok(responses);
	}
}
