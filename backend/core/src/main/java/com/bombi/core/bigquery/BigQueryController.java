package com.bombi.core.bigquery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bigquery")
@RequiredArgsConstructor
public class BigQueryController {

	private final BigQueryService bigQueryService;

	@GetMapping("/data")
	public String getBigQueryData() {
		log.info("빅쿼리 연결 시작");
		bigQueryService.queryBigQueryData();
		return "데이터 조회 성공";
	}
}
