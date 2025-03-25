package com.bombi.core.infrastructure.config;

import java.io.*;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

@Configuration
public class BigQueryConfig {

	@Value("${spring.cloud.gcp.bigquery.credentials-json}")
	private String credentialJson;

	@Bean
	public BigQuery bigQuery() throws IOException {
		InputStream inputStream = new ByteArrayInputStream(credentialJson.getBytes(StandardCharsets.UTF_8));  // 🔹 JSON 스트림 변환
		return BigQueryOptions.newBuilder()
				.setCredentials(ServiceAccountCredentials.fromStream(inputStream))  // 🔹 직접 인증
				.build()
				.getService();
	}
}
