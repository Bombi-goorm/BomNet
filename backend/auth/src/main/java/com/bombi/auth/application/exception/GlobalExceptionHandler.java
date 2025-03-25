package com.bombi.auth.application.exception;

import com.bombi.auth.application.exception.e400.BadRequestException;
import com.bombi.auth.application.exception.e401.InvalidTokenException;
import com.bombi.auth.application.exception.e500.RedisSessionException;
import com.bombi.auth.presentation.dto.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 BAD_REQUEST - 통합
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleIllegalArgumentException(BadRequestException ex) {
        log.warn("BadRequestException - " + ex.getMessage());
        CommonResponseDto<?> response = new CommonResponseDto<>("FAILURE", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 401 UNAUTHORIZED - 토큰관련
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidTokenException(InvalidTokenException ex) {
        log.warn("InvalidTokenException - " + ex.getMessage());
        CommonResponseDto<?> response = new CommonResponseDto<>("401", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 404 NOT FOUND - 없는 주소
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("NoResourceFoundException - " + ex.getMessage());
        CommonResponseDto<?> response = new CommonResponseDto<>("404", "잘못된 요청입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 500 INTERNAL_SERVER_ERROR - REDIS
    @ExceptionHandler(RedisSessionException.class)
    public ResponseEntity<?> handleRedisSessionException(RedisSessionException ex) {
        log.error("RedisSessionException - Redis 상태관리 실패, message={}", ex.getMessage(), ex);
        CommonResponseDto<?> response = new CommonResponseDto<>("500", "상태 관리 중 오류 발생.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // 500 INTERNAL_SERVER_ERROR - 기타 서버 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerError(Exception ex) {
        log.error("Exception - " + ex.getMessage());
        ex.printStackTrace();
        CommonResponseDto<?> response = new CommonResponseDto<>("500", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        ex.printStackTrace();  // 서버 로그에 전체 오류 메시지 출력 (디버깅용)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
