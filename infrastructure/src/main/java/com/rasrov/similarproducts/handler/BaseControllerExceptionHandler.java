package com.rasrov.similarproducts.handler;

import com.rasrov.similarproducts.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class BaseControllerExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<Map<String, String>> handleMissingParameter(final ApplicationException applicationException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("errorMessage", applicationException.getMessage()));
    }

}
