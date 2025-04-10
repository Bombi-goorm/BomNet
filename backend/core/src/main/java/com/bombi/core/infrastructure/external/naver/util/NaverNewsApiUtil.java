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

    private static final String NAVER_OPENAPI_URI = "https://openapi.naver.com";
    private static final String NAVER_OPENAPI_NEWS_PATH = "/v1/search/news.json";

    @Value("${naver.news.api.url}")
    private String NAVER_OCR_API_URL;

    @Value("${naver.client-id}")
    private String NAVER_CLIENT_ID;

    @Value("${naver.client-secret}")
    private String NAVER_CLIENT_SECRET;

    public HttpHeaders createNewsHeader() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", NAVER_CLIENT_ID);
        headers.set("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);

        return headers;
    }

    public URI createNewsUri(String display, String start) {
        return UriComponentsBuilder
            .fromUriString(NAVER_OPENAPI_URI)
            .path(NAVER_OPENAPI_NEWS_PATH)
            .queryParam("query", "농산물")
            .queryParam("sort", "sim")
            .queryParam("display", display)
            .queryParam("start", start)
            .encode()
            .build()
            .toUri();
    }
}
