package com.bombi.core.common.utils.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class DailyPriceTimeGenerator implements TimeGenerator {

	public static final int DEFAULT_DAILY_PERIOD = 30;

	@Override
	public LocalDateTime generateStartTime() {
		LocalDate localDate = LocalDate.now().minusDays(DEFAULT_DAILY_PERIOD);
		LocalTime localTime = LocalTime.of(0, 0);
		return LocalDateTime.of(localDate, localTime);
	}

	@Override
	public LocalDateTime generateEndTime() {
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.of(0, 0);
		return LocalDateTime.of(localDate, localTime);
	}
}
