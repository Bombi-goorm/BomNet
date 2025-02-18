package com.bombi.core.presentation.controller;

import com.bombi.core.application.service.NotificationService;
import com.bombi.core.common.dto.CoreResponseDto;
import com.bombi.core.presentation.dto.notify.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taco/core/notifications")
public class NotificationController {

    private final NotificationService notificationService;

}
