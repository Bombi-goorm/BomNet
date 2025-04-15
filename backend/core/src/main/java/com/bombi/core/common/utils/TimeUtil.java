package com.bombi.core.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

	private static final String LOCAL_DATE_TIME_STRING = "yyyy-MM-dd HH:mm:ss";

	public static String getSpecialWeatherReportStartTime() {
		LocalDate today = LocalDate.now();
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(today, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}

	public static String getSpecialWeatherReportEndTime() {
		LocalDate tomorrow = LocalDate.now().plusDays(1L);
		LocalTime localTime = LocalTime.of(3, 0, 0);

		LocalDateTime localDateTime = LocalDateTime.of(tomorrow, localTime);

		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}

	public static String getForecastStartTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}

	public static String getForecastEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now().plusHours(6L);
		return localDateTime.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING));
	}

	public static String getRealtimePriceStartTime() {
		LocalDate localDate = LocalDate.now().minusDays(2L);
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDateTime localDateTime = LocalDateTime.of(localDate, midnight);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}

	public static String getRealtimePriceEndTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_STRING);
		return localDateTime.format(formatter);
	}
}
