//package com.bombi.core.gcs;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//@RequestMapping("/gcs")
//@RequiredArgsConstructor
//public class GcsController {
//
//	private final GcsService gcsService;
//
//	@GetMapping("/data")
//	public String getBigQueryData() {
//		log.info("gcs 연결 시작");
//		gcsService.queryBigQueryData();
//		return "데이터 조회 성공";
//	}
//}
