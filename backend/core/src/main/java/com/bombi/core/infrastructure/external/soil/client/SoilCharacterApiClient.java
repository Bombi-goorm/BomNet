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

	// @Value("${api.soil.serviceKey}")
	private String serviceKey = "";

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

			String soilTypeCode = root.path("Soil_Type_Code").asText();
			String vldsoildepCode = root.path("Vldsoildep_Code").asText();
			String soildraCode = root.path("Soildra_Code").asText();

			return new SoilCharacterResponseDto(soilTypeCode, vldsoildepCode, soildraCode);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Xml 매핑 실패", e);
		}
	}

	private boolean isErrorResultCode(ResponseEntity<String> response) {
		return !("200".equals(response.getHeaders().get("Result_Code")));
	}
}
