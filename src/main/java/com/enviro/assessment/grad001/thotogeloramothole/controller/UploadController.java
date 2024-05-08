package com.enviro.assessment.grad001.thotogeloramothole.controller;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/api/file")
@Tag(name = "Environment Data API", description = "Upload and retrieve environment data files")
public class UploadController {

    private final FileService fileService;

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
    public ResponseEntity<Iterable<File>> getAllEnvironmentFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Upload an environment data file", description = "Upload an environment data file")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        fileService.storeFile(file);
        return ResponseEntity.ok("File uploaded successfully.");
    }
}