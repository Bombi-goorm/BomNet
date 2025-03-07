package com.bombi.core.fasttest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.FarmAnalyzer;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.presentation.dto.product.FarmSuitability;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FarmController {

	private final FarmAnalyzer farmAnalyzer;
	private final ProductRepository productRepository;

	@GetMapping("/farm")
	ResponseEntity<?> suitabileFarm() {
		Long midId = 6L;
		Long smallId = 12L;
		String pnuCode = "4617033028100130004";

		Product product = productRepository.findProductInfoByCategoryId(midId, smallId)
			.orElseThrow(() -> new IllegalArgumentException("작물 검색 실패"));
		FarmSuitability result = farmAnalyzer.analyzeSuitability(pnuCode, product);
		return ResponseEntity.ok(result);
	}
}
