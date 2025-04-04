package com.bombi.core.common.exception;

import com.bombi.core.common.dto.CoreResponseDto;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 내장되어 있는 예외들
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("handleIllegalArgumentException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("MethodArgumentNotValidException - " + ex.getMessage());
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        if (errorMessages.size() == 1) {
            // 오류 메시지가 하나면 String 형태로 반환
            CoreResponseDto response = new CoreResponseDto("FAILURE", errorMessages.get(0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            // 오류 메시지가 여러개인 경우
            CoreResponseDto response = new CoreResponseDto("FAILURE", "요청이 유효하지 않습니다.", errorMessages);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "요청 본문이 비어있거나 올바르지 않습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(DataIntegrityViolationException.class) // 데이터 무결성 제약 조건을 위반했을 때 발생하는 예외 (데이터베이스에 저장할 때 NULL 값이 들어가면 안 되는 칼럼에 NULL이 들어간 경우나 고유 제약 조건을 위반한 경우)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("DataIntegrityViolationException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "필수 데이터가 누락되었거나 잘못된 데이터가 입력되었습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(ConstraintViolationException.class) // 데이터베이스 제약 조건을 위반 등
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("ConstraintViolationException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "필수 데이터가 누락되었거나 잘못된 데이터가 입력되었습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.warn("NoResourceFoundException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "잘못된 요청입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.warn("HttpRequestMethodNotSupportedException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "지원하지 않는 요청입니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.warn("MaxUploadSizeExceededException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "최대 업로드 용량을 초과했습니다. (최대 5MB).");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Redis Session 예외처리
    @ExceptionHandler(RedisSessionException.class)
    public ResponseEntity<?> handleRedisSessionException(RedisSessionException ex) {
        log.warn("RedisSessionException - " + ex.getMessage());
        HttpStatus status = (ex.getHttpStatus() == null) ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getHttpStatus();
        String message = (status == HttpStatus.INTERNAL_SERVER_ERROR) ? "서버 내부 오류가 발생했습니다. 관리자에게 문의해주세요." : ex.getMessage();

        CoreResponseDto response = new CoreResponseDto(ex.getStatus(), message);
        return ResponseEntity.status(status).body(response);
    }

    // 멤버 인증 예외
    @ExceptionHandler(MemberAuthException.class)
    public ResponseEntity<?> handleMemberAuthException(MemberAuthException ex) {
        log.warn("MemberAuthException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    // 포괄적인 서버 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerError(Exception ex) {
        // 예외 정보
        String exceptionName = ex.getClass().getName(); // 예외 클래스 이름
        String exceptionMessage = ex.getMessage(); // 예외 메시지

        // 현재 스레드의 스택 트레이스 중 상위 3개 추출
        StackTraceElement[] stackTrace = ex.getStackTrace();
        String errorLocation = stackTrace.length > 0
                ? String.format("Class: %s, Method: %s, Line: %d",
                stackTrace[0].getClassName(),
                stackTrace[0].getMethodName(),
                stackTrace[0].getLineNumber())
                : "No stack trace available";

        // 로그 출력
        log.error("Exception occurred: [{}] - {}", exceptionName, exceptionMessage);
        log.error("Error location: {}", errorLocation);

        // 전체 스택 트레이스 (디버깅 용도)
        ex.printStackTrace();

        // 사용자에게 반환할 메시지
        CoreResponseDto response = new CoreResponseDto("FAILURE", "서버 내부 오류가 발생했습니다. 관리자에게 문의해주세요.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex) {
        log.warn("IllegalStateException - " + ex.getMessage());
        CoreResponseDto response = new CoreResponseDto("FAILURE", "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
