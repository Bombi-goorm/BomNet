package com.bombi.core.common.utils.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class realtimeGenerator implements TimeGenerator {

	@Override
	public LocalDateTime generateStartTime() {
		LocalDate localDate = LocalDate.now().minusDays(2L);
		LocalTime localTime = LocalTime.MIDNIGHT;

		return LocalDateTime.of(localDate, localTime);
	}

	@Override
	public LocalDateTime generateEndTime() {
		return LocalDateTime.now();
	}
}
