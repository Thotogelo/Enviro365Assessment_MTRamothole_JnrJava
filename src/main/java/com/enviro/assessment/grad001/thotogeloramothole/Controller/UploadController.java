package com.enviro.assessment.grad001.thotogeloramothole.Controller;

import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/v1/api/file")
public class UploadController {

    private final FileService fileService;

    public UploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Upload controller working");
    }

    @GetMapping("/all")
    public ResponseEntity<String> getAll() {
        fileService.getAllFiles().forEach(file -> {
            System.out.println("File Id: " + file.getId());
        });
        return ResponseEntity.ok("Upload controller working");
    }

    @GetMapping("/{fileid}")
    public ResponseEntity<byte[]> getProcessedData(@PathVariable Long fileid) {
        return ResponseEntity.ok(fileService.getProcessedDataById(fileid));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
//            System.out.println("File content: " + content);
            fileService.storeFile(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file." + e.getMessage());
        }

        return ResponseEntity.ok("File uploaded successfully");
    }
}
