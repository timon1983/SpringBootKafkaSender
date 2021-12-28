package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOMessage;
import org.junit.jupiter.api.DisplayName;
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
        Files.createFile(Path.of("test-files", fileName));

        assertTrue(clientCacheService.isCached(fileName, "test-files"));

        Files.deleteIfExists(Path.of("test-files", fileName));
        Files.deleteIfExists(Path.of("test-files"));

    }

    @Test
    @DisplayName("gfd gf gf")
    void check_setCache_Should_Set_File_To_Cache() throws IOException {
        DTOMessage dtoMessage = new DTOMessage();
        String actualFn = "abc.txt";
        dtoMessage.setFileNameForS3(actualFn);
        dtoMessage.setContent(new byte[]{1, 2, 3, 4});
        clientCacheService.setCache(dtoMessage, "test-files");
        Path path = Path.of("test-files", dtoMessage.getFileNameForS3());

        assertTrue(Files.exists(path));
        assertEquals(path.getFileName().toString(), actualFn);

        Files.deleteIfExists(path);
        // todo
        Files.deleteIfExists(path.getParent());
    }
}