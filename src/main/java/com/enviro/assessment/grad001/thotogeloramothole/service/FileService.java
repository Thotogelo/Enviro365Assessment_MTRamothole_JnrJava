package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.repository.FileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private static final long MAX_FILE_SIZE = 512000; // 500kb

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public ResponseEntity<Iterable<File>> getAllFiles() {
        LinkedList<File> files = (LinkedList<File>) fileRepository.findAll();
        return (files.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(files);
    }

    public ResponseEntity<File> getFileById(Long id) {
        Optional<File> file = fileRepository.findById(id);
        return file.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<File> storeFile(MultipartFile file) {
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
            return new ResponseEntity<>(fileToStore, HttpStatus.OK);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to process file." + file.getOriginalFilename(), e);
        }
    }

}
