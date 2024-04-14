package com.enviro.assessment.grad001.thotogeloramothole.controller;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/v1/api/file")
@Tag(name = "Environment Data API", description = "Upload and retrieve environment data files")
public class UploadController {

    private final FileService fileService;
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);


    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/{fileid}")
    @Operation(summary = "Get an environment data file by id", description = "Returns an environment data file by id")
    public ResponseEntity<?> getProcessedData(@PathVariable("fileid") Long fileid) {
        try {
            return ResponseEntity.ok(fileService.getFileById(fileid));
        } catch (FileProcessingException e) {
            logger.error("Error getting processed data by id: {}", fileid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the file: " + e.getMessage());
        }
    }

    @GetMapping("/data")
    @Operation(summary = "Get all environment data file", description = "Returns all environment data files")
    public ResponseEntity<?> getAllData() {
        try {
            List<File> files = fileService.getAllFiles();
            return ResponseEntity.ok(files);
        } catch (FileProcessingException e) {
            logger.error("An error occurred while retrieving the data: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving the data: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload an environment data file", description = "Upload an environment data file")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {

        try {
//            File empty validation
            if (file.isEmpty()) {
                logger.error("File is empty.");
                return ResponseEntity.badRequest().body("File is empty, please upload a text file with contents");
            }

            // File size validation
//            500 * 1024 = 512000; // 500KB
            if (file.getSize() > 512000) {
                logger.error("Provided a file larger than 500kb.");
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large, please upload a file smaller than 500kb.");
            }

//            File type validation
            if (!MediaType.TEXT_PLAIN.equals(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())))) {
                logger.error("Provided a non-text file.");
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Please upload a text file.");
            }

            fileService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (FileStorageException e) {
            logger.error("Failed to upload file.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file." + e.getMessage());
        }

    }
}