package com.bombi.auth.application.exception.e500;


public class RedisSessionException extends RuntimeException {
    public RedisSessionException(String message) {
        super(message);
    }
}
