package com.bombi.core.common.utils.time.generator;

import java.time.LocalDateTime;

public interface TimeGenerator {

	LocalDateTime generateStartTime();
	LocalDateTime generateEndTime();
}
