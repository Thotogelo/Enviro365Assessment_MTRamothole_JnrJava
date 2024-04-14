package com.enviro.assessment.grad001.thotogeloramothole;

import com.enviro.assessment.grad001.thotogeloramothole.service.FileService;
import com.enviro.assessment.grad001.thotogeloramothole.service.IFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private IFileService fileService;

    @Test
    void testEmptyFileUpload() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", "text/plain", "".getBytes());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileService.storeFile(emptyFile);
        });

        String expectedMessage = "File is empty, please upload a text file with contents";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testLargeFileUpload() {
        byte[] largeFileContent = new byte[512001]; // size > 500KB
        MockMultipartFile largeFile = new MockMultipartFile("file", "test.txt", "text/plain", largeFileContent);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileService.storeFile(largeFile);
        });

        String expectedMessage = "File is too large, please upload a file smaller than 500kb.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testNonTextFileUpload() {
        MockMultipartFile nonTextFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileService.storeFile(nonTextFile);
        });

        String expectedMessage = "Please upload a text file.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}