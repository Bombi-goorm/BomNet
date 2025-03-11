package com.bombi.core.application.service;

import org.springframework.stereotype.Component;

import com.bombi.core.application.dto.pushsubscription.UserAgentInfo;

import lombok.RequiredArgsConstructor;
import ua_parser.Client;
import ua_parser.Parser;

@Component
@RequiredArgsConstructor
public class CustomUserAgentParser {

	private final Parser parser;

	public UserAgentInfo extractUserAgentInfo(String userAgent) {
		Client client = parser.parse(userAgent);

		String browserName = client.userAgent.family; // 브라우저 이름
		String osName = client.os.family; // OS 이름
		if(osName.contains("Mobile") || osName.contains("iPhone")) {
			return new UserAgentInfo(browserName, osName, "Mobile");
		}

		return new UserAgentInfo(browserName, osName, "Desktop");
	}
}
