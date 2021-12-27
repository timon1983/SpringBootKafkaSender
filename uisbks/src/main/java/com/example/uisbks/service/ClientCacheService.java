package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public boolean isCached(String fileName , String path)  {
        try {
            return Files.list(Path.of(path))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .anyMatch(it -> it.equals(fileName));
        } catch (IOException e) {
            log.error("Ошибка при проверке кеша");
           throw new NoIdException("Ошибка при проверке кеша");
        }
    }

    /**
     * Запись файла в кэш
     */
    public void setCache(DTOMessage dtoMessage, String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        log.info("Запись файла {} в кэш", dtoMessage.getFileNameForS3());
        File file = Path.of(path, dtoMessage.getFileNameForS3()).toFile();
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(dtoMessage.getContent());
        } catch (IOException e) {
            log.error("Ошибка при записи файла {} кэш", dtoMessage.getFileNameForS3());
            throw new NoIdException("Ошибка при записи в кеш");
        }
    }
}
