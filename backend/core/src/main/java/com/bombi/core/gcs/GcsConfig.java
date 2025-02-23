package com.bombi.core.gcs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class GcsConfig {

	@Bean
	public Storage storage() throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("goorm-bomnet-20b400302eed.json");
		if (inputStream == null) {
			throw new FileNotFoundException("Resource bomnet-bigquery-llm.json not found");
		}
		return StorageOptions.newBuilder()
			.setCredentials(ServiceAccountCredentials.fromStream(inputStream))
			.build()
			.getService();
	}
}
