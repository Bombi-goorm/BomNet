package com.bombi.core.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

@Configuration
public class BigQueryConfig {

	@Bean
	public BigQuery bigQuery() throws IOException {
		return BigQueryOptions.newBuilder()
			.setCredentials(ServiceAccountCredentials.fromStream(
				new FileInputStream("src/main/resources/bomnet-bigquery-llm.json")))
			.build()
			.getService();
	}
}
