package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.common.dto.CoreResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core/products")
public class ProductController {

	@GetMapping("/cultivation")
	public ResponseEntity<CoreResponseDto<?>> cultivationInfo(@AuthenticationPrincipal UserDetails userDetails) {
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("생산 조건 조회 성공"));
	}
}
