package com.bombi.core.infrastructure.external.naver.client;

import java.net.URI;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bombi.core.common.exception.NaverOpenApiException;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.infrastructure.external.naver.util.NaverNewsApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverNewsApiClient {

    private final RestTemplate restTemplate;

    @Cacheable(value = "News")
    public NaverNewsResponse sendNews(URI uri, HttpEntity<Object> requestEntity) {
        // Naver News API 요청
        log.info("NaverNewsApiClient::sendNews CALL Naver News API");

        ResponseEntity<NaverNewsResponse> responseEntity = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            requestEntity,
            NaverNewsResponse.class
        );

        log.info("NaverNewsApiClient::sendNews response - {}", responseEntity);

        // 응답 결과가 실패인 경우
        if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("NaverNewsApiClient::sendNews Open Api ERROR - Response null or status is not ok");
            throw new NaverOpenApiException("news", "응답이 null이거나 유효하지 않습니다.");
        }

        log.info("NaverNewsApiClient::sendNews END");
        return responseEntity.getBody();
    }

}
