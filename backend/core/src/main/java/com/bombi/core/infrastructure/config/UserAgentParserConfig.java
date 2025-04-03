package com.bombi.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ua_parser.Parser;

@Configuration
public class UserAgentParserConfig {

	@Bean
	public Parser userAgentParser() {
		return new Parser();
	}

}
