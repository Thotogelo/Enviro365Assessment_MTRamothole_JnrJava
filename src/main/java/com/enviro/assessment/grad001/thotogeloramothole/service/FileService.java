package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.model.RequestFileDTO;
import com.enviro.assessment.grad001.thotogeloramothole.repository.FileRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private static final long MAX_FILE_SIZE = 512000; // 500kb

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public Iterable<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public File getFileById(UUID id) {
        try {
            Optional<File> file = fileRepository.getFileById(UUID.randomUUID());
            return file.orElseGet(File::new);
        } catch (Exception e) {
            throw new FileProcessingException("Error getting processed data by id: " + id, e);
        }
    }

    public void storeFile(RequestFileDTO file) {
        File fileToStore = new File(file);
        fileToStore.setProcessedData(readAndStoreFileContents(file));
        fileRepository.save(fileToStore);
    }

    private boolean validation(RequestFileDTO file) {
        if (file.requestFile().isEmpty()) {
            throw new FileStorageException("File is empty, please upload a text requestFile with contents");
        }

        if (file.requestFile().getSize() > MAX_FILE_SIZE) {
            throw new FileStorageException("File is too large, please upload a requestFile smaller than 500kb.");
        }

        if (!MediaType.TEXT_PLAIN.equals(MediaType.parseMediaType(Objects.requireNonNull(file.requestFile().getContentType())))) {
            throw new FileStorageException("File is not a text requestFile, please upload a text requestFile.");
        }

        return true;
    }

    private String readAndStoreFileContents(RequestFileDTO file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.requestFile().getInputStream()))) {

            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            return data.toString();
        } catch (IOException e) {
            throw new FileProcessingException("Failed to process requestFile." + file.requestFile().getOriginalFilename(), e);
        }
    }

}
