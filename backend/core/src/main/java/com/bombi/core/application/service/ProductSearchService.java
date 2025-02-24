package com.bombi.core.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.repository.MemberInfoRepository;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.domain.productionCondition.repository.ProductionConditionRepository;
import com.bombi.core.presentation.dto.product.ProductSearchResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

	private final ProductRepository productRepository;
	private final ProductionConditionRepository productionConditionRepository;
	private final MemberInfoRepository memberInfoRepository;


	@Transactional(readOnly = true)
	public ProductSearchResponseDto search(Long midId, Long smallId, String memberId) {
		//상품 정보 ProductInfo -> categoryid, categoryName, imageUrl, productId
		Product product = productRepository.findOneByCategoryId(midId, smallId)
			.orElseThrow(() -> new IllegalArgumentException("상품 정보를 찾을 수 없습니다."));

		//상품 재배 정보 CultivationInfo -> db


		//상품 판매량 ProductSales -> bigquery

		//농장 적합도 FarmSuitability -> 사용자 정보를 기반으로 빅쿼리에 요청

		return new ProductSearchResponseDto(product);
	}
}
