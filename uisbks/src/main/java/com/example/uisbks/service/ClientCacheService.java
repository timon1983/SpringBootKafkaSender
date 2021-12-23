package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Класс управления кэшем
 */
@Service
public class ClientCacheService {

    private final static Logger log = LogManager.getLogger(ClientCacheService.class);

    /**
     * Проверка наличия файла кэше
     */
    public boolean isCached(String fileName , String path) {
        File[] files = new File(path).listFiles();
        if (files != null) {
            return Arrays.stream(files)
                    .anyMatch(file -> file.getName().equals(fileName));
        }
        return false;
    }

    /**
     * Запись файла в кэш
     */
    public void setCache(DTOMessage dtoMessage, String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        log.info("Запись файла {} в кэш", dtoMessage.getFileNameForS3());
        File file = new File(String.join("",path, dtoMessage.getFileNameForS3()));
        //Files.createFile(Paths.get(String.join("",path, dtoMessage.getFileNameForS3())));
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(dtoMessage.getContent());
        } catch (IOException e) {
            log.error("Ошибка при записи файла {} кэш", dtoMessage.getFileNameForS3());
        }
    }
}
