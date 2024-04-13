package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.Exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.Exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.Models.File;
import com.enviro.assessment.grad001.thotogeloramothole.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> getAllFiles() {
        return fileRepository.getAllFiles();
    }

    public byte[] getProcessedDataById(Long id) {
        try {
            return fileRepository.getFileById(id).getProcessedData();
        } catch (Exception e) {
            throw new FileProcessingException("Error getting processed data by id: " + id, e);
        }
    }

    public void storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Failed to store empty file." + file.getOriginalFilename());
            }

            File fileToStore = new File();

            fileToStore.setFileName(file.getOriginalFilename());
            fileToStore.setProcessedData(file.getBytes());
            fileToStore.setUploadDate(Date.from(new Date().toInstant()));
            fileRepository.save(fileToStore);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to process file." + file.getOriginalFilename(), e);
        }
    }

}
