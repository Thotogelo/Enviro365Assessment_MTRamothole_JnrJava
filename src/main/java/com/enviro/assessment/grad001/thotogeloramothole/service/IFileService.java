package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFileService {
    List<File> getAllFiles();

    File getFileById(Long id);

    void storeFile(MultipartFile file);
}