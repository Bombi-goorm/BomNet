package com.bombi.core.common.utils.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class MonthlyPriceTimeGenerator implements TimeGenerator {

	public static final int DEFAULT_MONTH_PERIOD = 12;

	@Override
	public LocalDateTime generateStartTime() {
		LocalDate localDate = LocalDate.now().minusMonths(DEFAULT_MONTH_PERIOD);
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
