package com.rasrov.similarproducts.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class BaseControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> handleMissingParameter(final Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("errorMessage", exception.getMessage()));
    }

}
