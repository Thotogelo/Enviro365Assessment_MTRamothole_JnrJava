package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {
    // Retrieves all files
    List<File> getAllFiles();

    // Retrieves a file by its ID
    File getFileById(Long id);

    // Stores a file
    void storeFile(MultipartFile file);
}