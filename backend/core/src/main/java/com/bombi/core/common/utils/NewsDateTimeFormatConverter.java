package com.bombi.core.common.utils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NewsDateTimeFormatConverter {

	public static LocalDateTime changeDateFormat(String dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

		ZonedDateTime zdt = ZonedDateTime.parse(dateTime, formatter);
		return zdt.toLocalDateTime();
	}
}
