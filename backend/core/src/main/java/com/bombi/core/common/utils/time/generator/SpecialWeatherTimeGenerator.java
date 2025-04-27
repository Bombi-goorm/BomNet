package com.bombi.core.common.utils.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class SpecialWeatherTimeGenerator implements TimeGenerator {

	@Override
	public LocalDateTime generateStartTime() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0, 0);

		return LocalDateTime.of(today, localTime);
	}

	@Override
	public LocalDateTime generateEndTime() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0, 0);

		return LocalDateTime.of(tomorrow, localTime);
	}
}
