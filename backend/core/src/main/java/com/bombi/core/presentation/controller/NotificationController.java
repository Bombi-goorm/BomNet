package com.bombi.core.presentation.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.application.service.NotificationService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.infrastructure.security.authentication.CustomUserDetails;
import com.bombi.core.presentation.dto.notification.NotificationResponseDto;
import com.bombi.core.presentation.dto.notification.ReadNotificationRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	@GetMapping
	public ResponseEntity<CoreResponseDto<?>> getNotifications(
		@PageableDefault(size = 20) Pageable pageable,
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
        List<NotificationResponseDto> response = notificationService.getNotifications(pageable, userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("알림 목록 조회 성공", response));
	}

	@PostMapping
	public ResponseEntity<?> readOneNotification(
		@RequestBody ReadNotificationRequestDto requestDto,
		@AuthenticationPrincipal CustomUserDetails userDetails,
		Pageable pageable
	) {
		notificationService.markAsRead(requestDto);
		List<NotificationResponseDto> response = notificationService.getNotifications(pageable,
			userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("알림 읽기 성공", response));
	}

	@PostMapping("/all")
	public ResponseEntity<?> readAllNotification(@AuthenticationPrincipal CustomUserDetails userDetails, Pageable pageable) {
		notificationService.markAsReadAllNotification(userDetails.getUsername());
		List<NotificationResponseDto> response = notificationService.getNotifications(pageable,
			userDetails.getUsername());
		return ResponseEntity.ok(CoreResponseDto.ofSuccess("알림 전체 읽기 성공", response));
	}
}
