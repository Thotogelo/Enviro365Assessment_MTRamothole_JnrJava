package com.enviro.assessment.grad001.thotogeloramothole;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.service.IFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * It uses the Spring Boot testing framework to provide an application context for the tests.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFileService fileService;

    /**
     * This test case checks if the upload endpoint throws an exception when an empty file is uploaded.
     * It creates a mock empty file and expects a FileStorageException with a specific error message.
     */
    @Test
    void shouldThrowExceptionWhenUploadingEmptyFile() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", "".getBytes());
        String message = "File is empty, please upload a text file with contents";

        doThrow(new FileStorageException(message))
                .when(fileService).storeFile(emptyFile);

                
        // Performing a POST request to the upload endpoint with the empty file
        // Expecting a 400 Bad Request status and the error message
        mockMvc.perform(multipart("/v1/api/file/upload").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

    /**
     * This test case checks if the upload endpoint throws an exception when a large file is uploaded.
     * It creates a mock large file and expects a FileStorageException with a specific error message.
     */
    @Test
    void shouldThrowExceptionWhenUploadingLargeFile() throws Exception {
        byte[] largeFileContent = new byte[512001];
        MockMultipartFile largeFile = new MockMultipartFile("file", "test.txt", "text/plain", largeFileContent);
        String message = "File is too large, please upload a file smaller than 500kb.";

        doThrow(new FileStorageException(message))
                .when(fileService).storeFile(largeFile);

        // Performing a POST request to the upload endpoint with the large file
        // Expecting a 413 Payload Too Large status and the error message
        mockMvc.perform(multipart("/v1/api/file/upload").file(largeFile))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(content().string(message));
    }

    /**
     * This test case checks if the upload endpoint throws an exception when a non-text file is uploaded.
     * It creates a mock non-text file and expects a FileStorageException with a specific error message.
     */
    @Test
    void shouldThrowExceptionWhenUploadingNonTextFile() throws Exception {
        MockMultipartFile nonTextFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        String message = "File is not a text file, please upload a text file.";

        doThrow(new FileStorageException(message))
                .when(fileService).storeFile(nonTextFile);

        // Performing a POST request to the upload endpoint with the non-text file
        // Expecting a 415 Unsupported Media Type status and the error message
        mockMvc.perform(multipart("/v1/api/file/upload").file(nonTextFile))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().string(message));
    }
}