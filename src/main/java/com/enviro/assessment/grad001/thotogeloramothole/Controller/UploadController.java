package com.enviro.assessment.grad001.thotogeloramothole.Controller;

import com.enviro.assessment.grad001.thotogeloramothole.Models.File;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/api/file")
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }


    @GetMapping("/{fileid}")
    public ResponseEntity<File> getProcessedData(@PathVariable("fileid") Long fileid) {
        return ResponseEntity.ok(fileService.getFileById(fileid));
    }

    @GetMapping("/data")
    public ResponseEntity<?> getAllData() {

        try {
            List<File> files = fileService.getAllFiles();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            // Return an error response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve data.");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty, please upload a text file with contents");
            }

            if (!MediaType.TEXT_PLAIN.equals(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())))) {
                return ResponseEntity.badRequest().body("Please upload a text file.");
            }

            fileService.storeFile(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file." + e.getMessage());
        }

        return ResponseEntity.ok("File uploaded successfully");
    }
}