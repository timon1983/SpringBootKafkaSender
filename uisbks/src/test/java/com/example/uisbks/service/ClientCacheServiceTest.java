package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ClientCacheService.class)
class ClientCacheServiceTest {

    @Autowired
    ClientCacheService clientCacheService;
    String fileName = "abc.txt";

    @Test
    @Order(1)
    void check_isCached_Should_Return_Boolean(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve(fileName);
        Files.createFile(file);

        assertTrue(clientCacheService.isCached(fileName, tempDir.toString()));
    }

    @Test
    @Order(2)
    @DisplayName("Метод должен поместить файл в кеш")
    void check_setCache_Should_Set_File_To_Cache(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve(fileName);
        DTOMessage dtoMessage = new DTOMessage();
        dtoMessage.setFileNameForS3(fileName);
        dtoMessage.setContent("test-line".getBytes(StandardCharsets.UTF_8));
        clientCacheService.setCache(dtoMessage, tempDir.toString());

        assertTrue(Files.exists(file));
        assertEquals(file.getFileName().toString(), fileName);
        assertEquals(Files.lines(file).findFirst().orElseThrow(NullPointerException::new), "test-line");
    }
}