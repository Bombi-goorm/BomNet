package com.bombi.core.common.exception;

import org.springframework.http.HttpStatus;

public class MemberAuthException extends BaseCustomException {

    public MemberAuthException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
