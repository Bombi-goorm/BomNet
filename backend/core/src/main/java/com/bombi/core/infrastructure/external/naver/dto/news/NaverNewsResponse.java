package com.bombi.core.infrastructure.external.naver.dto.news;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverNewsResponse {

	private String lastBuildDate; // 검색 결과 생성 시간
	private int total;           // 총 결과 개수
	private int start;           // 검색 시작 위치
	private int display;         // 한 번에 표시할 검색 결과 개수
	private List<NewsInfo> items;     // 실제 뉴스 아이템 리스트
}
