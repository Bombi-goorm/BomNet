package com.bombi.core.infrastructure.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

@Configuration
public class BigQueryConfig {

	@Bean
	public BigQuery bigQuery() throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bomnet-bigquery-llm.json");
		if (inputStream == null) {
			throw new FileNotFoundException("Resource bomnet-bigquery-llm.json not found");
		}
		return BigQueryOptions.newBuilder()
			.setCredentials(ServiceAccountCredentials.fromStream(inputStream))
			.build()
			.getService();
	}
}
