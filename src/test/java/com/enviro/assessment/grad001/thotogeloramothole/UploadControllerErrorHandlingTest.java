package com.enviro.assessment.grad001.thotogeloramothole;

import com.enviro.assessment.grad001.thotogeloramothole.exception.FileStorageException;
import com.enviro.assessment.grad001.thotogeloramothole.model.File;
import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerErrorHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @Test
    void testEmptyFileUpload() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", "".getBytes());

        mockMvc.perform(multipart("/v1/api/file/upload").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("File is empty, please upload a text file with contents"));
    }

    @Test
    void testLargeFileUpload() throws Exception {
        byte[] largeFileContent = new byte[512001]; // size > 500KB
        MockMultipartFile largeFile = new MockMultipartFile("file", "test.txt", "text/plain", largeFileContent);

        mockMvc.perform(multipart("/v1/api/file/upload").file(largeFile))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(content().string("File is too large, please upload a file smaller than 500kb."));
    }

    @Test
    void testNonTextFileUpload() throws Exception {
        MockMultipartFile nonTextFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        mockMvc.perform(multipart("/v1/api/file/upload").file(nonTextFile))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(content().string("Please upload a text file."));
    }

}
