package com.liquibase.demo.response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> responseHandler(String message, HttpStatus httpStatus, Object response) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("message", message);
        map.put("httpStatus", httpStatus.value() + " " + httpStatus.getReasonPhrase());
        map.put("data", response);
        return new ResponseEntity<>(map, httpStatus);
    }
}
