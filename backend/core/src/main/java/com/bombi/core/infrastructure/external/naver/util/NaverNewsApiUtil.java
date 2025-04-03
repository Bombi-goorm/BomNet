package com.bombi.core.infrastructure.external.naver.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NaverNewsApiUtil {

    @Value("${naver.news.api.url}")
    private String NAVER_OCR_API_URL;

    @Value("${naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    public HttpHeaders createNewsHeader() {
        log.info("NaverApiUtil::createNewsHeader START");
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.set("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        log.info("NaverApiUtil::createNewsHeader END");

        return headers;
    }

    public URI createNewsUri(String display, String start) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.set("query", "농산물");
        queryParams.set("display", display);
        queryParams.set("start", start);
        queryParams.set("sort", "sim");

        return UriComponentsBuilder
            .fromUriString("https://openapi.naver.com")
            .path("/v1/search/news.json")
            // .queryParams(queryParams)
            .queryParam("query", "농산물")
            .queryParam("sort", "sim")
            .queryParam("display", 10)
            .queryParam("start", 1)
            .encode()
            .build()
            .toUri();
    }
}
