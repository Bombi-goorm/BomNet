package com.bombi.core.infrastructure.external.naver.dto.news;

import com.bombi.core.fasttest.navernews.TagEliminator;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsInfo {

	private String title;         // 기사 제목
	private String originallink;  // 기사 원문 URL
	private String link;          // 네이버 뉴스 기사 URL
	private String description;   // 기사 요약
	private String pubDate;       // 기사 게재 일자 (dateTime)
}
