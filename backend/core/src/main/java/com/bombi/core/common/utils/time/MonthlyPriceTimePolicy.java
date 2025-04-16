package com.bombi.core.common.utils.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.bombi.core.common.constants.TimeConstants;

@Component
public class MonthlyPriceTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDate localDate = LocalDate.now().minusMonths(12);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_STRING);
		return localDate.format(formatter);
	}

	@Override
	public String getEndTime() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_STRING);
		return localDate.format(formatter);
	}
}
