package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.WeatherService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.member.MemberInfoResponseDto;
import com.bombi.core.presentation.dto.weather.WeatherNoticeResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core/weather")
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping("/notice")
	public ResponseEntity<CoreResponseDto<?>> weatherNotice(@AuthenticationPrincipal UserDetails userDetails) {
		WeatherNoticeResponseDto responseDto = weatherService.findNotice(userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("사용자 조회 성공", responseDto));
	}
}
