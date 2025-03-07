package com.bombi.core.infrastructure.external.soil.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
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
import com.bombi.core.infrastructure.external.soil.dto.SoilChemicalResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SoilChemicalApiClient {

	private static final String REQUEST_URL = "http://apis.data.go.kr";
	private static final String REQUEST_PATH = "/1390802/SoilEnviron/SoilExam/getSoilExam";

	private final RestTemplate restTemplate;
	private final XmlMapper xmlMapper = new XmlMapper();

	@Value("${api.soil.serviceKey}")
	private String serviceKey;

	public SoilChemicalResponseDto sendSoilChemical(String pnuCode) {
		log.info("SoilChemicalApiClient::sendSoilChemical START");

		// HttpHeaders 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

		log.info("SoilChemicalApiClient::sendSoilChemical Headers - {}", headers);

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
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

			if (isErrorResultCode(response) || response.getBody() == null) {
				throw new SoilCharacterApiException("응답이 null이거나 유효하지 않습니다.");
			}

			SoilChemicalResponseDto responseDto = mapXmlToDto(response);

			return responseDto;
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			throw new SoilCharacterApiException(ex.getMessage(), ex);
		}

	}

	private SoilChemicalResponseDto mapXmlToDto(ResponseEntity<String> response) {
		try {
			JsonNode root = xmlMapper.readTree(response.getBody());

			String resultCode = root.get("header").get("result_Code").asText();
			if (!("200".equals(resultCode))) {
				return null;
			}

			JsonNode bodyJsonNode = root.get("body").get("items").get("item");

			String pH = bodyJsonNode.path("ACID").asText();
			String vldphaMgPerKg = bodyJsonNode.path("VLDPHA").asText(); // 유효인산
			String organicMatterGPerKg = bodyJsonNode.path("OM").asText(); // 유기물
			String posifertKCMolPerKg = bodyJsonNode.path("POSIFERT_K").asText(); // 칼륨
			String posifertCaCMolPerKg = bodyJsonNode.path("POSIFERT_CA").asText(); // 칼슘
			String posifertMgCMolPerKg = bodyJsonNode.path("POSIFERT_MG").asText(); // 마그네슘

			return new SoilChemicalResponseDto(pH, vldphaMgPerKg, organicMatterGPerKg, posifertKCMolPerKg,
				posifertCaCMolPerKg, posifertMgCMolPerKg);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Xml 매핑 실패", e);
		}
	}

	private boolean isErrorResultCode(ResponseEntity<String> response) {
		return !response.getStatusCode().is2xxSuccessful();
	}
}
