package com.bombi.core.application.dto.pushsubscription;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAgentInfo {
	private String browserName;
	private String osName;
	private String deviceType;

	public UserAgentInfo(String browserName, String osName, String deviceType) {
		this.browserName = browserName;
		this.osName = osName;
		this.deviceType = deviceType;
	}
}
