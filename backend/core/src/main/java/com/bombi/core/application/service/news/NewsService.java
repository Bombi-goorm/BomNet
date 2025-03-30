package com.bombi.core.application.service.news;

import org.springframework.stereotype.Service;

import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsService {

	private final NaverNewsApiClient naverNewsApiClient;

	public NaverNewsResponse getNews() {
		return naverNewsApiClient.sendNews();
	}
}
