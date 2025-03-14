package com.bombi.core.fasttest.pricechart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product/chart")
public class PriceController {

	private final PriceChartNodeApiClient priceChartNodeApiClient;
	private final PriceChartLinkApiClient priceChartLinkApiClient;

	@GetMapping("/node")
	public ResponseEntity<?> priceNodeChart() {
		PriceChartNodeResponse response = priceChartNodeApiClient.sendChartNode();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/link")
	public ResponseEntity<?> priceLinkChart() {
		PriceChartLinkResponse response = priceChartLinkApiClient.sendChartLink();
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<?> priceLinkAndNodeChart() {
		priceChartLinkApiClient.getChartLinkAndNode();
		return ResponseEntity.ok().build();
	}
}
