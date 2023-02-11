package com.server.exception.handler;

import com.server.exception.FileAlreadyInvoicedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleCSVParseError(IOException io) {
        return new ResponseEntity<>("Error occurred while parsing file %s"
                .formatted(io.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileAlreadyInvoicedException.class)
    public ResponseEntity<Object> handleFileAlreadyInvoicedException(FileAlreadyInvoicedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
