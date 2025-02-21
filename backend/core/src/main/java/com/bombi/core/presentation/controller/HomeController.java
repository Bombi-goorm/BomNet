package com.bombi.core.presentation.controller;

import com.bombi.core.application.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    /**
     * 홈 화면 데이터
     * - Best5 or 관심품목5
     * - 특보목록
     * - 예보데이터
     * - 뉴스목록
     * - 챗봇 공지사항
     */
    @GetMapping("/home")
    public ResponseEntity<Comm> getUserAccounts() {
        AccountMemberReponseDto responseDto = homeService.getMemberHome();
        return ResponseEntity.ok(responseDto);
    }


}
