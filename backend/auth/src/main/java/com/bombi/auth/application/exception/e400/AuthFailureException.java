package com.bombi.auth.application.exception.e400;


public class AuthFailureException extends RuntimeException {
    private final String count;

    public AuthFailureException(String message, int count) {
        super(message);
        this.count = String.valueOf(count);
    }

    public String getCount() {
        return count;
    }
}
