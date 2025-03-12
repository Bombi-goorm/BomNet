package com.bombi.core.infrastructure.external.naver.client;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.bombi.core.common.exception.NewsFailedException;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.infrastructure.external.naver.util.NaverNewsApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverNewsApiClient {

    private final NaverNewsApiUtil naverNewsApiUtil;
    private final RestTemplate restTemplate;

    public NaverNewsResponse sendNews() {
        log.info("NaverNewsApiClient::sendNews START");
        URI uri = naverNewsApiUtil.createNewsUri("10", "1");

        // HttpHeaders 생성
        HttpHeaders headers = naverNewsApiUtil.createNewsHeader();
        log.info("NaverNewsApiClient::sendNews Headers - {}", headers);

        // HttpEntity 생성
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        log.info("NaverNewsApiClient::sendNews requestEntity - {}", requestEntity);

        // Naver News API 요청
        try {
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
                throw new NewsFailedException("응답이 null이거나 유효하지 않습니다.");
            }

            log.info("NaverNewsApiClient::sendNews END");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("NaverNewsApiClient::sendNews Open Api ERROR - {}", e.getResponseBodyAsString());
            throw new NewsFailedException(e.getMessage(), e);
        }  catch (Exception e) {
            log.error("NaverNewsApiClient::sendNews ERROR - {}", e.getMessage());
            throw new NewsFailedException("알 수 없는 오류 발생: " + e.getMessage(), e);
        }
    }

}
