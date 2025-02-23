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

    public URI createNewsUri() {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.set("query", "농산물");
        queryParams.set("display", "10");
        queryParams.set("start", "1");
        queryParams.set("sort", "sim");

        return UriComponentsBuilder
            .fromUriString(NAVER_OCR_API_URL)
            .queryParams(queryParams)
            .encode()
            .build()
            .toUri();
    }
}
