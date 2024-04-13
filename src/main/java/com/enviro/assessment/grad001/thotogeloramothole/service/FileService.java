package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.Exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.Exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.Models.File;
import com.enviro.assessment.grad001.thotogeloramothole.Models.ProcessedData;
import com.enviro.assessment.grad001.thotogeloramothole.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Failed to store empty file." + file.getOriginalFilename());
            }

            File fileToStore = new File();
            ProcessedData processedData = new ProcessedData();

            fileToStore.setFileName(file.getOriginalFilename());
            processedData.setFileId(fileToStore.getId());
            processedData.setProcessedData(file.getBytes());
            fileRepository.save(fileToStore);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to process file." + file.getOriginalFilename(), e);
        }
    }

}
