package com.bombi.core.common.utils.time.provider;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bombi.core.common.utils.time.formatter.TimeFormatter;
import com.bombi.core.common.utils.time.generator.TimeGenerator;

@Component
public class MonthlyTimeStringProvider implements TimeStringProvider {

	private final TimeGenerator timeGenerator;

	public MonthlyTimeStringProvider(@Qualifier("monthlyPriceTimeGenerator") TimeGenerator timeGenerator) {
		this.timeGenerator = timeGenerator;
	}

	@Override
	public String getStartDateString() {
		LocalDateTime startDate = timeGenerator.generateStartTime();
		return TimeFormatter.formatToDateString(startDate);
	}

	@Override
	public String getEndDateString() {
		LocalDateTime endDate = timeGenerator.generateEndTime();
		return TimeFormatter.formatToDateString(endDate);
	}

}
