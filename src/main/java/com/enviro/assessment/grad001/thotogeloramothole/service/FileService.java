package com.enviro.assessment.grad001.thotogeloramothole.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.repository.FileRepository;

@Service
public class FileService implements IFileService {
    private final FileRepository fileRepository;
    private static final long MAX_FILE_SIZE = 512000; // 500kb

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public File getFileById(Long id) {
        try {
            return fileRepository.getFileById(id);
        } catch (Exception e) {
            throw new FileProcessingException("Error getting processed data by id: " + id, e);
        }
    }

    @Override
    public void storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("File is empty, please upload a text file with contents");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileStorageException("File is too large, please upload a file smaller than 500kb.");
        }

        if (!MediaType.TEXT_PLAIN.equals(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())))) {
            throw new FileStorageException("File is not a text file, please upload a text file.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            StringBuilder data = new StringBuilder();
            String line;
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
