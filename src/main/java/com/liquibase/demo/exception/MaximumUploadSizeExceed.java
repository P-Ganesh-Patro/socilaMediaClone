package com.liquibase.demo.exception;

public class MaximumUploadSizeExceed extends RuntimeException {
    public MaximumUploadSizeExceed(String message) {
        super(message);
    }
}
