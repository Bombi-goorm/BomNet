package com.bombi.core.common.utils.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

@Component
public class ForecastTimeGenerator implements TimeGenerator {

	public static final long FORECAST_PERIOD = 6L;

	@Override
	public LocalDateTime generateStartTime() {
		return LocalDateTime.now();
	}

	@Override
	public LocalDateTime generateEndTime() {
		return LocalDateTime.now().plusHours(FORECAST_PERIOD);
	}
}
