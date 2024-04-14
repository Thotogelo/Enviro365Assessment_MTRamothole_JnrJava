package com.enviro.assessment.grad001.thotogeloramothole.controller;

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
    private static final long MAX_FILE_SIZE = 512000; // 500KB

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping(value = "/{fileid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get an environment data file by id", description = "Returns an environment data file by id")
    public ResponseEntity<File> getFilesById(@PathVariable("fileid") Long fileid) {
        return ResponseEntity.ok(fileService.getFileById(fileid));
    }

    @GetMapping(value = "/data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all environment data file", description = "Returns all environment data files")
    public ResponseEntity<List<File>> getAllEnvironmentFiles() {
        List<File> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload an environment data file", description = "Upload an environment data file")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
//            File empty validation
        if (file.isEmpty()) {
            logger.error("File is empty.");
            return ResponseEntity.badRequest().body("File is empty, please upload a text file with contents");
        }

        // File size validation
//            500 * 1024 = 512000; // 500KB
        if (file.getSize() > MAX_FILE_SIZE) {
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
    }
}