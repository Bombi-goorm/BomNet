package com.bombi.core.common.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.bombi.core.common.constants.TimeConstants;

@Component
public class SpecialReportTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(today, localTime);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}

	@Override
	public String getEndTime() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(tomorrow, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_TIME_STRING));
	}
}
