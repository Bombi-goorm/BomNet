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
import com.bombi.core.infrastructure.external.naver.dto.ocr.NaverReceiptOcrResponseDto;
import com.bombi.core.infrastructure.external.naver.util.NaverNewsApiUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverNewsApiClient {

    private final NaverNewsApiUtil naverNewsApiUtil;
    private final RestTemplate restTemplate;

    public NaverNewsResponse sendNews() {
        log.info("NaverOcrApiClient::sendOcrReceipt START");
        URI uri = naverNewsApiUtil.createNewsUri();

        // HttpHeaders 생성
        HttpHeaders headers = naverNewsApiUtil.createNewsHeader();
        log.info("NaverOcrApiClient::sendOcrReceipt Headers - {}", headers);


        // HttpEntity 생성
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        log.info("NaverOcrApiClient::sendOcrReceipt requestEntity - {}", requestEntity);

        // Naver News API 요청
        try {
            log.info("NaverOcrApiClient::sendOcrReceipt CALL Naver OCR API");
            ResponseEntity<NaverNewsResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                NaverNewsResponse.class
            );
            log.info("NaverOcrApiClient::sendOcrReceipt Naver OCR API response - {}", responseEntity);


            // 응답 결과가 실패인 경우
            if (responseEntity == null || responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new NewsFailedException("응답이 null이거나 유효하지 않습니다.");
            }

            log.info("NaverOcrApiClient::sendOcrReceipt END");
            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new NewsFailedException(ex.getMessage(), ex);

        }  catch (Exception ex) {
            ex.printStackTrace();
            throw new NewsFailedException("알 수 없는 오류 발생: " + ex.getMessage(), ex);
        }

    }

    public NaverReceiptOcrResponseDto mapJsonToDto(String json) {
        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(json, NaverReceiptOcrResponseDto.class);
        } catch (Exception e) {
            throw new RuntimeException("JSON 매핑 실패", e);
        }
    }

}
