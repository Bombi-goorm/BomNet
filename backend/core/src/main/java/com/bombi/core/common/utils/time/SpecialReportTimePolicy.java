package com.bombi.core.common.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class SpecialReportTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(today, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}

	@Override
	public String getEndTime() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(tomorrow, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}
}
