package com.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IOException.class)
    protected ResponseEntity<Object> handleCSVParseError(IOException io, WebRequest request) {
        return new ResponseEntity<>("Error occurred while parsing file %s"
                .formatted(io.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
