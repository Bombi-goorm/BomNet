package com.bombi.core.infrastructure.external.naver.dto.news;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverNewsResponse {

	private Rss rss;

	@Getter
	@NoArgsConstructor
	public static class Rss {
		private Channel channel;
	}

	@Getter
	@NoArgsConstructor
	public static class Channel {
		private String lastBuildDate; // 검색 결과 생성 시간 (dateTime)
		private int total;           // 총 결과 개수
		private int start;           // 검색 시작 위치
		private int display;         // 한 번에 표시할 검색 결과 개수
		private List<Item> item;     // 실제 뉴스 아이템 리스트
	}

	@Getter
	@NoArgsConstructor
	public static class Item {
		private String title;         // 기사 제목
		private String originalLink;  // 기사 원문 URL
		private String link;          // 네이버 뉴스 기사 URL
		private String description;   // 기사 요약
		private String pubDate;       // 기사 게재 일자 (dateTime)
	}
}
