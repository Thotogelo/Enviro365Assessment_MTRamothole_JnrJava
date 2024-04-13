package com.enviro.assessment.grad001.thotogeloramothole.service;

import com.enviro.assessment.grad001.thotogeloramothole.Exception.FileProcessingException;
import com.enviro.assessment.grad001.thotogeloramothole.repository.ProcessedDataRepository;
import org.springframework.stereotype.Service;

@Service
public class ProcessedDataService {

    ProcessedDataRepository processedDataRepository;

    public ProcessedDataService(ProcessedDataRepository processedDataRepository) {
        this.processedDataRepository = processedDataRepository;
    }

    public byte[] getProcessedDataById(Long id) {
        try {
            return processedDataRepository.getProcessedDataById(id);
        } catch (Exception e) {
            throw new FileProcessingException("Error getting processed data by id: " + id, e);
        }
    }
}
