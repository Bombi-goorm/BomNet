package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.HomeService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.home.HomeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {

	private final HomeService homeService;

	@GetMapping("/core/hoem")
	public ResponseEntity<CoreResponseDto<?>> home(@AuthenticationPrincipal UserDetails userDetails) {
		HomeResponseDto responseDto = homeService.homeInfo(userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.of("200", "홈화면 응답", responseDto));
	}

}
