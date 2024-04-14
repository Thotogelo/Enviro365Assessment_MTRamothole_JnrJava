package com.enviro.assessment.grad001.thotogeloramothole.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.repository.FileRepository;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public File getFileById(Long id) {
        try {
            return fileRepository.getFileById(id);
        } catch (Exception e) {
            throw new FileProcessingException("Error getting processed data by id: " + id, e);
        }
    }

    public void storeFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            if (file.isEmpty()) {
                throw new FileStorageException("Failed to store empty file." + file.getOriginalFilename());
            }

            StringBuilder data = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            File fileToStore = new File();
            fileToStore.setFileName(file.getOriginalFilename());
            fileToStore.setProcessedData(data.toString());
            fileToStore.setUploadDate(Date.from(new Date().toInstant()));

            fileRepository.save(fileToStore);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to process file." + file.getOriginalFilename(), e);
        }
    }

}
