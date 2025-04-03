package com.bombi.core.fasttest.navernews;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.infrastructure.external.naver.dto.news.NewsInfo;
import com.bombi.core.presentation.dto.home.NewsResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NaverNewsController {

	private final NaverNewsApiClient client;
	private final TagEliminator tagEliminator;

	@GetMapping("/naver/news")
	ResponseEntity<?> productNews() {
		NaverNewsResponse response = client.sendNews();
		List<NewsResponseDto> responseDtos = response.getItems()
			.stream()
			.map(NewsResponseDto::new)
			.toList();
		return ResponseEntity.ok(responseDtos);
	}

}
