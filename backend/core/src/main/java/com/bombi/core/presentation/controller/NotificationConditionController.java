package com.bombi.core.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.notification.NotificationConditionService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.infrastructure.security.authentication.CustomUserDetails;
import com.bombi.core.presentation.dto.notification.NotificationConditionResponseDto;
import com.bombi.core.presentation.dto.notification.OffConditionRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/condition")
public class NotificationConditionController {

	private final NotificationConditionService notificationConditionService;

	@PostMapping
	public ResponseEntity<?> turnOffCondition(@RequestBody OffConditionRequestDto requestDto,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		notificationConditionService.turnOffOne(requestDto);
		List<NotificationConditionResponseDto> response = notificationConditionService.findActiveConditions(
			userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("알림 조건 끄기 성공", response));
	}
}
