package com.server.exception;

public class InvalidFileFormatException extends Exception{
    public InvalidFileFormatException() {
        super("Invalid file format.");
    }
}
