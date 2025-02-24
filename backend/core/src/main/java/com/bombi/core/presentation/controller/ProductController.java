package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.ProductSearchService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.product.ProductSearchResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core/products")
public class ProductController {

	private final ProductSearchService productSearchService;

	@GetMapping
	public ResponseEntity<CoreResponseDto<?>> search(
		@RequestParam(value = "midId") Long midId,
		@RequestParam(value = "smallId") Long smallId,
		@AuthenticationPrincipal UserDetails userDetails) {
		ProductSearchResponseDto responseDto = productSearchService.search(midId, smallId, userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("생산 조건 조회 성공"));
	}
}
