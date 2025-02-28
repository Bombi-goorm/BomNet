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
		//상품 재배 정보 CultivationInfo
		Product product = productRepository.findProductInfoByCategoryId(midId, smallId)
			.orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

		//상품 판매량 ProductSales
		//사용자


		//농장 적합도 FarmSuitability
		//pnu코드와 api를 호출해 농장 상태(토양, 기온)를 가져온 후 작물의 생산조건과 비교해 응답

		return new ProductSearchResponseDto(product);
	}
}
