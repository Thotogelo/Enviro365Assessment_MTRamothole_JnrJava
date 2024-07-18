package com.enviro.assessment.grad001.thotogeloramothole.controller;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.model.RequestFileDTO;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/file")
@Tag(name = "Environment Data API", description = "Upload and retrieve environment data files")
public class UploadController {

    public final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/{fileid}")
    public ResponseEntity<File> getFilesById(@PathVariable("fileid") UUID fileid) {
        return ResponseEntity.ok(fileService.getFileById(fileid));
    }

    @GetMapping(value = "/data")
    public ResponseEntity<Iterable<File>> getAllEnvironmentFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fileUpload(@RequestParam("requestFile") RequestFileDTO requestFile) {
        fileService.storeFile(requestFile);
        return ResponseEntity.ok("File uploaded successfully.");
    }
}