package com.bombi.core.common.utils.time.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.bombi.core.common.constants.TimeConstants;

public class TimeFormatter {

	private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_STRING);
	private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TimeConstants.LOCAL_DATE_TIME_STRING);

	public static String formatToDateTimeString(LocalDateTime localDateTime) {
		return localDateTime.format(LOCAL_DATE_TIME_FORMATTER);
	}

	public static String formatToDateString(LocalDateTime localDateTime) {
		return localDateTime.format(LOCAL_DATE_FORMATTER);
	}
}
