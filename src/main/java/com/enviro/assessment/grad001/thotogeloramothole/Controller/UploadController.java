package com.enviro.assessment.grad001.thotogeloramothole.Controller;

import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import com.enviro.assessment.grad001.thotogeloramothole.service.ProcessedDataService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/api/file")
public class UploadController {

    private final FileService fileService;
    private final ProcessedDataService processedDataService;

    public UploadController(FileService fileService, ProcessedDataService processedDataService) {
        this.fileService = fileService;
        this.processedDataService = processedDataService;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Upload controller working");
    }

    @GetMapping("/{fileid}")
    public ResponseEntity<byte[]> getProcessedData(@PathVariable Long fileid) {
        return ResponseEntity.ok(processedDataService.getProcessedDataById(fileid));
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
