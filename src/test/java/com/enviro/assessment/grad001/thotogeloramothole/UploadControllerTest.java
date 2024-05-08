package com.enviro.assessment.grad001.thotogeloramothole;

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

/**
 * This class contains test cases for the UploadController class.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;
    private File file;

    /**
     * Set up method to initialize common test data
     */
    @BeforeEach
    public void setUp() {
        // Creating a new file with test data
        file = new File();
        file.setFileName("test.txt");
        file.setProcessedData("test data");
    }

    /**
     * This test case checks if the upload endpoint works correctly.
     * It creates a mock multipart file and expects a 200 OK status and a success message.
     */
    @Test
    void shouldUploadFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        mockMvc.perform(multipart("/v1/api/file/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully."));

        verify(fileService, times(1)).storeFile(any());
    }

    /**
     * This test case checks if the file retrieval endpoint works correctly for a specific file ID.
     * It simulates the getFileById method to return a test file and expects a 200 OK status and the test file data.
     */
    @Test
    void shouldRetrieveFileById() throws Exception {
        when(fileService.getFileById(1L)).thenReturn(file);

        mockMvc.perform(get("/v1/api/file/{fileid}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"fileName\":\"test.txt\",\"processedData\":\"test data\"}"));

        verify(fileService, times(1)).getFileById(1L);
    }

    /**
     * This test case checks if the file retrieval endpoint works correctly for all files.
     * It simulates the getAllFiles method to return a list containing a test file and expects a 200 OK status and the test file data.
     */
    @Test
    void shouldRetrieveAllFiles() throws Exception {
        when(fileService.getAllFiles()).thenReturn(Collections.singletonList(file));

        mockMvc.perform(get("/v1/api/file/data").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"fileName\":\"test.txt\",\"processedData\":\"test data\"}]"));

        verify(fileService, times(1)).getAllFiles();
    }
}