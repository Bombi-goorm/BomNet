package com.bombi.core.fasttest.navernews;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bombi.core.infrastructure.external.naver.client.NaverNewsApiClient;
import com.bombi.core.infrastructure.external.naver.dto.news.NaverNewsResponse;
import com.bombi.core.infrastructure.external.naver.dto.news.NewsInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NaverNewsController {

	private final NaverNewsApiClient client;
	private final TagEliminator tagEliminator;

	@GetMapping("/naver/news")
	ResponseEntity<?> productNews() {
		NaverNewsResponse response = client.sendNews();
		response.getItems().forEach(NewsInfo::eliminateTag);
		return ResponseEntity.ok(response);
	}

}
