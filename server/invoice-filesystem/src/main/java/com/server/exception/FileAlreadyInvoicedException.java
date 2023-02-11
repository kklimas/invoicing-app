package com.server.exception;

public class FileAlreadyInvoicedException extends RuntimeException{
    public FileAlreadyInvoicedException(String message) {
        super(message);
    }
}
