package com.bombi.core.presentation.controller;

// import com.bombi.core.application.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taco/core/home")
@RequiredArgsConstructor
public class HomeController {

    // private final HomeService homeService;
    //
    // /**
    //  * 사용자 계좌 및 거래 내역 조회
    //  */
    // @GetMapping
    // public ResponseEntity<AccountMemberReponseDto> getUserAccounts() {
    //     AccountMemberReponseDto responseDto = homeService.getMemberHome();
    //     return ResponseEntity.ok(responseDto);
    // }


}
