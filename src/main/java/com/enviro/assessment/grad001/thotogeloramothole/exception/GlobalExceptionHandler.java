package com.enviro.assessment.grad001.thotogeloramothole.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handles exceptions related to file processing
    @ExceptionHandler(FileProcessingException.class)
    public ProblemDetail handleFileProcessingException(FileProcessingException e) {
        logger.error("Error occurred", e);
        // Returns a response with a status of 500 and a message describing the error
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An error occurred: " + e.getMessage());
    }

    // Handles exceptions related to file storage
    @ExceptionHandler(FileStorageException.class)
    public ProblemDetail handleFileStorageException(FileStorageException ex) {
        // Determines the status code based on the exception message
        HttpStatus status = switch (ex.getMessage()) {
            case "File is empty, please upload a text file with contents" -> HttpStatus.BAD_REQUEST;
            case "File is too large, please upload a file smaller than 500kb." -> HttpStatus.PAYLOAD_TOO_LARGE;
            case "File is not a text file, please upload a text file." -> HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        logger.error(ex.getMessage(), ex);
        // Returns a response with the determined status code and the exception message
        return ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    }
}
