package com.enviro.assessment.grad001.thotogeloramothole.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<String> handleFileProcessingException(FileProcessingException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<String> handleFileStorageException(FileStorageException ex) {
        // Determine the status code based on the exception message
        HttpStatus status = switch (ex.getMessage()) {
            case "File is empty, please upload a text file with contents" -> HttpStatus.BAD_REQUEST;
            case "File is too large, please upload a file smaller than 500kb." -> HttpStatus.PAYLOAD_TOO_LARGE;
            case "Please upload a text file." -> HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
