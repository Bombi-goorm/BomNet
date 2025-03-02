package com.bombi.core.infrastructure.external.weather.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bombi.core.infrastructure.external.weather.dto.EnvWeatherResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvWeatherInfoApiClient {

	private static final String REQUEST_URL = "https://www.bigdata-environment.kr";
	private static final String REQUEST_PATH = "/user/openapi/api.call.do";
	private final RestTemplate restTemplate;

	/**
	 * pnu를 가져와 region테이블에서 조회한 후 stationName을 기준으로 작업
	 * @param stationName
	 * @return
	 */
	public EnvWeatherResponseDto sendWeatherInfo(String stationName) {
		log.info("EnvWeatherInfoApiClient::sendWeatherInfo START");

		// HttpHeaders 생성
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		log.info("EnvWeatherInfoApiClient::sendWeatherInfo Headers - {}", headers);

		// parameter 추가
		// URI uri = UriComponentsBuilder
		// 	.fromUriString(REQUEST_URL)
		// 	.path(REQUEST_PATH)
		// 	.queryParam("serviceKey", serviceKey)
		// 	.queryParam("PNU_Code", pnuCode)
		// 	.encode()
		// 	.build()
		// 	.toUri();
		//
		// try {
		// 	ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		//
		// 	if (isErrorResultCode(response) || response.getBody() == null) {
		// 		throw new SoilCharacterApiException("응답이 null이거나 유효하지 않습니다.");
		// 	}
		//
		// 	SoilChemicalResponseDto responseDto = mapXmlToDto(response);
		//
		// 	return responseDto;
		// } catch (HttpClientErrorException | HttpServerErrorException ex) {
		// 	throw new SoilCharacterApiException(ex.getMessage(), ex);
		// }
		return null;
	}
}
