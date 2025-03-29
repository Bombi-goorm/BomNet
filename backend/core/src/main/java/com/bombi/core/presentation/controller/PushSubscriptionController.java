package com.bombi.core.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.push.PushSubscriptionService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.member.SubscriptionRegisterRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PushSubscriptionController {

	private final PushSubscriptionService pushSubscriptionService;

	@PostMapping("/member/push")
	public ResponseEntity<?> subscribe(@RequestHeader("User-Agent") String userAgent,
		@RequestBody SubscriptionRegisterRequestDto requestDto,
		@AuthenticationPrincipal UserDetails userDetails) {
		pushSubscriptionService.register(userAgent, requestDto, userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("구독 성공"));
	}
}
