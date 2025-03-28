package com.bombi.core.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.presentation.dto.product.FarmSuitability;
import com.bombi.core.presentation.dto.product.ProductSearchRequestDto;
import com.bombi.core.presentation.dto.product.ProductSearchResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final FarmAnalyzer farmAnalyzer;

	/**
	 * 사용자가 검색한 작물 정보와 생산 적합도 판별
	 * @param requestDto : 중분류, 소분류
	 */
	@Transactional(readOnly = true)
	public ProductSearchResponseDto search(ProductSearchRequestDto requestDto, String memberId) {
		//상품 재배 정보 CultivationInfo
		Product product = productRepository.findProductInfoByItemAndVariety(requestDto.getItem(), requestDto.getVariety())
			.orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
		Member member = memberRepository.findMemberAndInfoById(UUID.fromString(memberId))
			.orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

		//농장 적합도 FarmSuitability
		// String pnuCode = member.getMemberInfo().getPnu();
		FarmSuitability farmSuitability = farmAnalyzer.analyzeSuitability(requestDto.getPnu(), product);

		return new ProductSearchResponseDto(product, farmSuitability);
	}
}
