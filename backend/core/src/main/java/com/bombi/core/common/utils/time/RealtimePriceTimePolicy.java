package com.bombi.core.common.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class RealtimePriceTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDate localDate = LocalDate.now().minusDays(2L);
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDateTime localDateTime = LocalDateTime.of(localDate, midnight);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}

	@Override
	public String getEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}
}
