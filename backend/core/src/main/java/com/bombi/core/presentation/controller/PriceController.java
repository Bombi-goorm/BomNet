package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.PriceService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.price.OverallPriceInfoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PriceController {

	private final PriceService priceService;

	@PostMapping("/core/item/price")
	public ResponseEntity<?> overallPrice() {
		OverallPriceInfoResponse response = priceService.findAllPrice();
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("가격 정보 조회 성공", response));
	}


}
