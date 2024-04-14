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

@SpringBootTest
@AutoConfigureMockMvc
class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;
    private File file;

    @BeforeEach
    public void setUp() {
        file = new File();
        file.setFileName("test.txt");
        file.setProcessedData("test data");
    }

    @Test
    void testFileUpload() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        mockMvc.perform(multipart("/v1/api/file/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully."));

        verify(fileService, times(1)).storeFile(any());
    }

    @Test
    void testGetFilesById() throws Exception {
        when(fileService.getFileById(1L)).thenReturn(file);

        mockMvc.perform(get("/v1/api/file/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"fileName\":\"test.txt\",\"processedData\":\"test data\"}"));

        verify(fileService, times(1)).getFileById(1L);
    }

    @Test
    void testGetAllEnvironmentFiles() throws Exception {
        when(fileService.getAllFiles()).thenReturn(Collections.singletonList(file));

        mockMvc.perform(get("/v1/api/file/data").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"fileName\":\"test.txt\",\"processedData\":\"test data\"}]"));

        verify(fileService, times(1)).getAllFiles();
    }
}