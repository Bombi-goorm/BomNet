package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.InterestService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.interest.InterestProductResponseDto;
import com.bombi.core.presentation.dto.interest.RegisterInterestRequestDto;
import com.bombi.core.presentation.dto.interest.RemoveInterestRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class InterestController {

	private final InterestService interestService;

	@GetMapping("/interest")
	public ResponseEntity<CoreResponseDto<?>> interestProducts(@AuthenticationPrincipal UserDetails userDetails) {
		InterestProductResponseDto responseDto = interestService.findInterestProducts(userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("관심품목 조회 성공", responseDto));
	}

	@PostMapping("/interest")
	public ResponseEntity<CoreResponseDto<?>> registerInterestProduct(
		@RequestBody RegisterInterestRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		interestService.registerProduct(requestDto, userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("관심품목 등록 성공"));
	}

	@DeleteMapping("/interest")
	public ResponseEntity<CoreResponseDto<?>> removeInterestProduct(
		@RequestBody RemoveInterestRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails
	) {
		interestService.removeProduct(requestDto, userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("관심품목 삭제 성공"));
	}
}
