package com.bombi.core.common.utils.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.bombi.core.common.constants.TimeConstants;

@Component
public class ForecastTimePolicy implements TimePolicy {

	@Override
	public String getStartTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}

	@Override
	public String getEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().plusHours(6L);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}
}
