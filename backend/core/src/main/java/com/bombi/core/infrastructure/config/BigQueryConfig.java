package com.bombi.core.infrastructure.config;

import java.io.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;

@Configuration
public class BigQueryConfig {

	@Value("${cloud.gcp.bigquery.credentials}")
	private String credentialJson;

	@Bean
	public BigQuery bigQuery() throws IOException {
		InputStream inputStream = new ByteArrayInputStream(credentialJson.getBytes());
		return BigQueryOptions.newBuilder()
				.setCredentials(ServiceAccountCredentials.fromStream(inputStream))
				.build()
				.getService();
	}
}
