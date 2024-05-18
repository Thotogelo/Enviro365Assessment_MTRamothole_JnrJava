package com.enviro.assessment.grad001.thotogeloramothole.controller;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/v1/api/file")
@Tag(name = "Environment Data API")
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/{fileid}")
    public ResponseEntity<File> getFileById(@PathVariable("fileid") Long fileid) {
        return fileService.getFileById(fileid);
    }

    @GetMapping(value = "/data")
    public ResponseEntity<Iterable<File>> getAllEnvironmentFiles() {
        return fileService.getAllFiles();
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<File> fileUpload(@RequestParam("file") MultipartFile file) {
        return fileService.storeFile(file);
    }
}