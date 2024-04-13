package com.enviro.assessment.grad001.thotogeloramothole.Exception;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message, Exception e) {
        super(message);
    }
}
