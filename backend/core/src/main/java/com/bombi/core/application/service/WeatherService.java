package com.bombi.core.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bombi.core.presentation.dto.weather.WeatherNoticeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherService {

	@Transactional(readOnly = true)
	public WeatherNoticeResponseDto findNotice(String username) {


		return null;
	}



}
