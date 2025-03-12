package com.bombi.core.presentation.dto.home;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.bombi.core.common.utils.NewsDateTimeFormatConverter;
import com.bombi.core.fasttest.navernews.TagEliminator;
import com.bombi.core.infrastructure.external.naver.dto.news.NewsInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsResponseDto {

	private String title;
	private String content;
	private LocalDateTime dateTime;
	private String newsLink;

	public NewsResponseDto(NewsInfo item) {
		this.title = TagEliminator.eliminateHtmlTag(item.getTitle());
		this.content = TagEliminator.eliminateHtmlTag(item.getDescription());
		this.dateTime = NewsDateTimeFormatConverter.changeDateFormat(item.getPubDate());
		this.newsLink = (item.getLink() == null ? item.getOriginallink() : item.getLink());
	}
}
