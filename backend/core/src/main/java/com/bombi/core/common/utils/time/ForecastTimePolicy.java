package com.bombi.core.common.utils.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class ForecastTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}

	@Override
	public String getEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().plusHours(6L);
		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}
}
