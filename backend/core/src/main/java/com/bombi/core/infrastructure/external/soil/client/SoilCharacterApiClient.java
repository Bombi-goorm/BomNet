package com.bombi.core.infrastructure.external.soil.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bombi.core.common.exception.SoilCharacterApiException;
import com.bombi.core.infrastructure.external.soil.dto.SoilCharacterResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SoilCharacterApiClient {

	private static final String REQUEST_URL = "http://apis.data.go.kr";
	private static final String REQUEST_PATH = "/1390802/SoilEnviron/SoilCharac/V2/getSoilCharacter";

	private final RestTemplate restTemplate;
	private final XmlMapper xmlMapper = new XmlMapper();

	@Value("${api.soil.serviceKey}")
	private String serviceKey;

	@Cacheable(value = "Soil", key = "#pnuCode + '_character'")
	public SoilCharacterResponseDto sendSoilCharacter(String pnuCode) {
		log.info("SoilCharacterApiClient::sendSoilCharacter START");

		// HttpHeaders 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

		log.info("SoilCharacterApiClient::sendSoilCharater Headers - {}", headers);

		// parameter 추가
		URI uri = UriComponentsBuilder
			.fromUriString(REQUEST_URL)
			.path(REQUEST_PATH)
			.queryParam("serviceKey", serviceKey)
			.queryParam("PNU_Code", pnuCode)
			.encode()
			.build()
			.toUri();


		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

			if (isErrorResultCode(response) || response.getBody() == null) {
				throw new SoilCharacterApiException("응답이 null이거나 유효하지 않습니다.");
			}

			SoilCharacterResponseDto responseDto = mapXmlToDto(response);

			return responseDto;
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			throw new SoilCharacterApiException(ex.getMessage(), ex);
		}

	}

	private SoilCharacterResponseDto mapXmlToDto(ResponseEntity<String> response) {
		try {
			JsonNode root = xmlMapper.readTree(response.getBody());

			String resultCode = root.get("header").get("result_Code").asText();
			if (!("200".equals(resultCode))) {
				return null;
			}

			JsonNode bodyJsonNode = root.get("body").get("items").get("item");
			String soilTypeCode = bodyJsonNode.path("Soil_Type_Code").asText(); // 토양 유형
			String vldsoildepCode = bodyJsonNode.path("Vldsoildep_Code").asText(); // 유효 토심 코드
			String soildraCode = bodyJsonNode.path("Soildra_Code").asText(); // 배수 등급 코드
			String surttureCode = bodyJsonNode.path("Surtture_Code").asText();// 표토 토성 코드

			return new SoilCharacterResponseDto(soilTypeCode, vldsoildepCode, soildraCode, surttureCode);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Xml 매핑 실패", e);
		}
	}

	private boolean isErrorResultCode(ResponseEntity<String> response) {
		return !response.getStatusCode().is2xxSuccessful();
	}
}
