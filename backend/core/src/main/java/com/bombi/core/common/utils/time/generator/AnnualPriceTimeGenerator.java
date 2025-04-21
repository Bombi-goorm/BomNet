package com.bombi.core.common.utils.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class AnnualPriceTimeGenerator implements TimeGenerator {

	public static final int DEFAULT_YEAR_PERIOD = 10;

	@Override
	public LocalDateTime generateStartTime() {
		LocalDate localDate = LocalDate.now().minusYears(DEFAULT_YEAR_PERIOD);
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
