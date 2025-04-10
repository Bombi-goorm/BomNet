package com.bombi.core.application.service.region;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bombi.core.common.exception.EntityNotFoundByPropertyException;
import com.bombi.core.domain.region.model.Region;
import com.bombi.core.domain.region.repository.RegionRepository;
import com.bombi.core.presentation.dto.home.HomeRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionRepository regionRepository;

	@Transactional(readOnly = true)
	public Region findRegionInfo(HomeRequestDto requestDto) {

		if(requestDto == null || !StringUtils.hasText(requestDto.getPnu())) {
			return regionRepository.findByStationName("서울")
				.orElseThrow(() -> new EntityNotFoundByPropertyException("region", "stationName", "서울"));
		}

		String weatherSiGunGuCode = requestDto.getPnu().substring(0, 5);

		return regionRepository.findByWeatherSiGunGuCode(weatherSiGunGuCode)
			.orElseThrow(() -> new EntityNotFoundByPropertyException("region", "siGunGuCode", weatherSiGunGuCode));
	}
}
