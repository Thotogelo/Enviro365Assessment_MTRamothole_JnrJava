package com.enviro.assessment.grad001.thotogeloramothole.Controller;

import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/api/file")
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String test() {
        return "Upload controller working";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }
            fileService.storeFile(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file.");
        }

        return ResponseEntity.ok("File uploaded successfully");
    }
}
