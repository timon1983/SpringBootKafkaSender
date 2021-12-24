package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOMessage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientCacheServiceTest {

    ClientCacheService clientCacheService = new ClientCacheService();

    @Test
    void check_isCached_Should_Return_Boolean() throws IOException {
        String fileName = "abc.txt";

        Files.createDirectories(Paths.get("test-files"));
        Files.createFile(Paths.get(String.format("test-files/%s", fileName)));

        assertTrue(clientCacheService.isCached(fileName, "test-files"));

        Files.deleteIfExists(Path.of(String.format("test-files/%s", fileName)));
        Files.deleteIfExists(Path.of("test-files"));

    }

    @Test
    void check_setCache_Should_Set_File_To_Cache() throws IOException {
        DTOMessage dtoMessage = new DTOMessage();
        dtoMessage.setFileNameForS3("abc.txt");
        dtoMessage.setContent(new byte[]{1, 2, 3, 4});
        clientCacheService.setCache(dtoMessage, "test-files/");
        File file = new File(String.format("test-files/%s", dtoMessage.getFileNameForS3()));

        assertTrue(file.exists());
        assertEquals(file.getName(), "abc.txt");

        Files.deleteIfExists(Path.of(String.format("test-files/%s", dtoMessage.getFileNameForS3())));
        Files.deleteIfExists(Path.of("test-files"));

    }
}