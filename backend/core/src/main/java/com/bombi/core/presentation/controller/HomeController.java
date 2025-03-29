package com.bombi.core.presentation.controller;

import com.bombi.core.presentation.dto.home.HomeRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.home.HomeService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.home.HomeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {

	private final HomeService homeService;

	@PostMapping("/core/home")
	public ResponseEntity<CoreResponseDto<?>> home(@RequestBody(required = false) HomeRequestDto requestDto) {
		HomeResponseDto responseDto = homeService.homeInfo(requestDto);
		return ResponseEntity.ok(CoreResponseDto.of("200", "홈화면 응답", responseDto));
	}
}
