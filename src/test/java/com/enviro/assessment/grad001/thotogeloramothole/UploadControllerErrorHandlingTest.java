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

@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFileService fileService;

    @Test
    void testEmptyFileUpload() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", "".getBytes());
        String message = "File is empty, please upload a text file with contents";

        doThrow(new FileStorageException(message))
                .when(fileService).storeFile(emptyFile);

        mockMvc.perform(multipart("/v1/api/file/upload").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

    @Test
    void testLargeFileUpload() throws Exception {
        byte[] largeFileContent = new byte[512001]; // size > 500KB
        MockMultipartFile largeFile = new MockMultipartFile("file", "test.txt", "text/plain", largeFileContent);
        String message = "File is too large, please upload a file smaller than 500kb.";

        doThrow(new FileStorageException(message))
                .when(fileService).storeFile(largeFile);

        mockMvc.perform(multipart("/v1/api/file/upload").file(largeFile))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(content().string(message));
    }

    @Test
    void testNonTextFileUpload() throws Exception {
        MockMultipartFile nonTextFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());
        String message = "Please upload a text file.";

        doThrow(new FileStorageException(message))
                .when(fileService).storeFile(nonTextFile);

        mockMvc.perform(multipart("/v1/api/file/upload").file(nonTextFile))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().string(message));
    }

}