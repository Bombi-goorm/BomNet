package com.bombi.core.common.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.bombi.core.common.constants.TimeConstants;

@Component
public class MonthlyPriceTimePolicy implements TimePolicy {

	public static final int DEFAULT_MONTH_PERIOD = 12;

	@Override
	public String getStartTime() {
		LocalDate localDate = LocalDate.now().minusMonths(DEFAULT_MONTH_PERIOD);
		LocalTime localTime = LocalTime.of(0, 0);
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_STRING);
		return localDateTime.format(formatter);
	}

	@Override
	public String getEndTime() {
		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.of(0, 0);
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_STRING);
		return localDateTime.format(formatter);
	}
}
