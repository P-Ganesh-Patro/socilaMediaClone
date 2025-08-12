package com.liquibase.demo.exception;

import org.springframework.http.HttpStatus;

public class InstagramException {
    private final String message;
    private final HttpStatus httpStatus;
    private final Throwable throwable;

    public InstagramException(String message, HttpStatus httpStatus, Throwable throwable) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.throwable = throwable;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
