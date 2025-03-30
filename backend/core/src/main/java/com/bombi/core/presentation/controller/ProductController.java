package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.product.ProductSearchService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.product.ProductSearchRequestDto;
import com.bombi.core.presentation.dto.product.ProductSearchResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class ProductController {

	private final ProductSearchService productSearchService;

	@PostMapping("/item/info")
	public ResponseEntity<CoreResponseDto<?>> search(
		@RequestBody ProductSearchRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails) {
		ProductSearchResponseDto response = productSearchService.search(requestDto, userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("생산 조건 조회 성공", response));
	}
}
