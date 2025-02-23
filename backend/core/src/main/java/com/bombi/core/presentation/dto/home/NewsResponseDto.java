package com.bombi.core.presentation.dto.home;

import com.bombi.core.infrastructure.external.naver.dto.news.NewsInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsResponseDto {

	private String title;
	private String content;
	private String dateTime;
	private String newsLink;

	public NewsResponseDto(NewsInfo item) {
		this.title = item.getTitle();
		this.content = item.getDescription();
		this.dateTime = item.getPubDate();
		this.newsLink = (item.getLink() == null ? item.getOriginallink() : item.getLink());
	}
}
