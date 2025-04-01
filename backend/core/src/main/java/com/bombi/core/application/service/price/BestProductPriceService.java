package com.bombi.core.application.service.price;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.infrastructure.external.bigquery.client.BestProductPriceApiClient;
import com.bombi.core.presentation.dto.home.ProductPriceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BestProductPriceService {

	private final ProductRepository productRepository;
	private final BestProductPriceApiClient bestProductApiClient;

	@Transactional(readOnly = true)
	public List<ProductPriceResponse> getBestProductPrice() {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("id"));
		List<Product> bestProducts = productRepository.findTop5ByCategoryId(pageRequest);
		List<String> midCategoryNames = bestProducts.stream()
			.map(product -> product.getCategory().getParent().getName())
			.collect(Collectors.toList());

		return bestProductApiClient.callBestProductPrice(midCategoryNames);
	}
}
