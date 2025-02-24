package com.bombi.core.infrastructure.external.gcs.client;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.bombi.core.common.exception.WeatherForecastException;
import com.bombi.core.infrastructure.external.gcs.dto.SpecialWeatherReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpecialWeatherReportApiClient {

	private static final String BUCKET_NAME = "kma_wrn";
	private static final String JSON_FILE_FORMAT = ".json";

	private final Storage storage;
	private final ObjectMapper objectMapper;

	public SpecialWeatherReportResponse sendSpecialWeatherReport() {
		String objectName = createObjectName();

		// 버킷에서 객체(파일) 가져오기
		Blob blob = storage.get(BUCKET_NAME, objectName);
		if (blob == null) {
			throw new WeatherForecastException("버킷에 객체가 존재하지 않습니다.");
		}

		SpecialWeatherReportResponse response = mapJsonToDto(blob);
		return response;
	}

	private String createObjectName() {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return today.format(formatter) + JSON_FILE_FORMAT;
	}

	private SpecialWeatherReportResponse mapJsonToDto(Blob blob) {
		String jsonContent = new String(blob.getContent(), StandardCharsets.UTF_8);

		try {
			return objectMapper.readValue(jsonContent, SpecialWeatherReportResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 매핑 실패", e);
		}
	}
}
