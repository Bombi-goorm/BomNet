package com.bombi.core.common.utils.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DailyPriceTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDate localDate = LocalDate.now().minusDays(30);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_STRING);
		return localDate.format(formatter);
	}

	@Override
	public String getEndTime() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_STRING);
		return localDate.format(formatter);
	}
}
