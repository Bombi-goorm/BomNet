package com.bombi.core.infrastructure.external.gcs.client;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.bombi.core.common.exception.WeatherForecastException;
import com.bombi.core.infrastructure.external.gcs.dto.WeatherForecastResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherForecastApiClient {

	private static final String BUCKET_NAME = "bomnet_forecast";
	private static final String JSON_FILE_FORMAT = ".jsonl";

	private final Storage storage;
	private final ObjectMapper objectMapper;

	public WeatherForecastResponse sendWeatherForecast() {
		String objectName = createObjectName();

		// 버킷에서 객체(파일) 가져오기
		Blob blob = storage.get(BUCKET_NAME, objectName);
		if (blob == null) {
			throw new WeatherForecastException("버킷에 객체가 존재하지 않습니다.");
		}

		WeatherForecastResponse response = mapJsonToDto(blob);
		return response;
	}

	private String createObjectName() {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return today.format(formatter) + JSON_FILE_FORMAT;
	}

	private WeatherForecastResponse mapJsonToDto(Blob blob) {
		String jsonContent = new String(blob.getContent(), StandardCharsets.UTF_8);

		try {
			return objectMapper.readValue(jsonContent, WeatherForecastResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 매핑 실패", e);
		}
	}
}
