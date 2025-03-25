package com.bombi.core.fasttest.weatherforecast;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bombi.core.common.exception.NewsFailedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForecastApiClient {

	private final RestTemplate restTemplate;

	@Value("${api.soil.serviceKey}")
	private String serviceKey;

	public ResponseEntity<ForecastApiResponse> sendWeatherForecast(int nx, int ny) {
		String requestUrl = "http://apis.data.go.kr";
		String path = "/1360000/VilageFcstInfoService_2.0/getVilageFcst";

		log.info("ForecastApiClient::sendWeatherForecast START");
		URI uri = UriComponentsBuilder
			.fromUriString(requestUrl)
			.path(path)
			.queryParam("serviceKey", serviceKey)
			.queryParam("numOfRows", "10")
			.queryParam("pageNo", "1")
			.queryParam("dataType", "JSON")
			.queryParam("base_date", "20250325")
			.queryParam("base_time", "0500")
			.queryParam("nx", 127)
			.queryParam("ny", 60)
			.encode()
			.build()
			.toUri();

		// weather forecast API 요청
		try {
			log.info("ForecastApiClient::sendWeatherForecast CALL API");
			ResponseEntity<ForecastApiResponse> responseEntity = restTemplate.getForEntity(uri, ForecastApiResponse.class);
			log.info("ForecastApiClient::sendWeatherForecast response - {}", responseEntity);

			// 응답 결과가 실패인 경우
			if (responseEntity.getStatusCode() != HttpStatus.OK) {
				throw new NewsFailedException("응답이 null이거나 유효하지 않습니다.");
			}

			log.info("ForecastApiClient::sendWeatherForecast END");
			return responseEntity;
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error("ForecastApiClient::sendWeatherForecast Open Api ERROR - {}", e.getResponseBodyAsString());
			throw new IllegalArgumentException(e.getMessage(), e);
		}  catch (Exception e) {
			log.error("ForecastApiClient::sendNews ERROR - {}", e.getMessage());
			throw new RuntimeException("알 수 없는 오류 발생: " + e.getMessage(), e);
		}
	}
}
