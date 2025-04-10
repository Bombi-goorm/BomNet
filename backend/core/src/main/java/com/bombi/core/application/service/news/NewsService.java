package com.bombi.core.application.service.news;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.bombi.core.common.exception.NaverOpenApiException;
import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.infrastructure.external.naver.util.NaverNewsApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

	private final NaverNewsApiUtil naverNewsApiUtil;
	private final NaverNewsApiClient naverNewsApiClient;

	public NaverNewsResponse getNews() {
		URI uri = naverNewsApiUtil.createNewsUri("10", "1");

		HttpHeaders headers = naverNewsApiUtil.createNewsHeader();
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

		try {
			return naverNewsApiClient.sendNews(uri, requestEntity);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error("NaverNewsApiClient::sendNews Open Api ERROR - {}", e.getResponseBodyAsString());
			throw new NaverOpenApiException("news", e.getMessage(), e);
		}  catch (Exception e) {
			log.error("NaverNewsApiClient::sendNews ERROR - {}", e.getMessage());
			throw new NaverOpenApiException("news", "알 수 없는 오류 발생: " + e.getMessage(), e);
		}
	}
}
