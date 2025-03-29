package com.bombi.core.application.service.interest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.domain.category.model.Category;
import com.bombi.core.domain.interest.model.InterestProduct;
import com.bombi.core.domain.interest.repository.InterestProductRepository;
import com.bombi.core.domain.member.model.Member;
import com.bombi.core.domain.member.repository.MemberRepository;
import com.bombi.core.domain.product.ProductRepository;
import com.bombi.core.domain.product.model.Product;
import com.bombi.core.presentation.dto.interest.InterestProductResponseDto;
import com.bombi.core.presentation.dto.interest.RegisterInterestRequestDto;
import com.bombi.core.presentation.dto.interest.RemoveInterestRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterestService {

	private final InterestProductRepository interestProductRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public InterestProductResponseDto findInterestProducts(String username) {
		// 관심품목 찾기
		List<Product> products = interestProductRepository.findByMemberId(UUID.fromString(username))
			.stream()
			.map(InterestProduct::getProduct)
			.collect(Collectors.toList());

		// 소분류, 중분류 추출
		List<Category> lowCategories = products.stream().map(Product::getCategory).collect(Collectors.toList());
		List<Category> midCategories = lowCategories.stream().map(Category::getParent).collect(Collectors.toList());

		return new InterestProductResponseDto(products, midCategories, lowCategories);
	}

	@Transactional
	public void registerProduct(RegisterInterestRequestDto requestDto, String username) {
		Member member = memberRepository.findById(UUID.fromString(username))
			.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
		Product product = productRepository.findById(requestDto.getProductId())
			.orElseThrow(() -> new IllegalArgumentException("해당하는 품목이 없습니다."));

		InterestProduct interestProduct = new InterestProduct(member, product);
		interestProductRepository.saveAndFlush(interestProduct);
	}

	@Transactional
	public void removeProduct(RemoveInterestRequestDto requestDto, String username) {
		UUID memberId = UUID.fromString(username);
		interestProductRepository.deleteByMemberIdAndProductId(memberId, requestDto.getProductId());
	}
}
