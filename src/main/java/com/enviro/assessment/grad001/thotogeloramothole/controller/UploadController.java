package com.enviro.assessment.grad001.thotogeloramothole.controller;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
@RequestMapping("/v1/api/file")
@Tag(name = "Environment Data API")
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/{fileid}")
    public ResponseEntity<File> getFilesById(@PathVariable("fileid") Long fileid) {
        Optional<File> file = fileService.getFileById(fileid);
        return file.isPresent() ? ResponseEntity.ok(file.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/data")
    public ResponseEntity<Iterable<File>> getAllEnvironmentFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        fileService.storeFile(file);
        return ResponseEntity.ok("File uploaded successfully.");
    }
}