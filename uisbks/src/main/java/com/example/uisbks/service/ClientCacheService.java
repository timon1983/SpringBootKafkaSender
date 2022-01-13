package com.example.uisbks.service;

import com.example.uisbks.dtomodel.MessageDto;
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

/**
 * Класс управления кэшем
 */
@Service
public class ClientCacheService {

    private final static Logger log = LogManager.getLogger(ClientCacheService.class);

    /**
     * Проверка наличия файла кэше
     */
    public boolean isCached(String fileName, String path) {
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
    public void setCache(MessageDto messageDto, String path) throws IOException {
        Files.createDirectories(Paths.get(path));
        log.info("Запись файла {} в кэш", messageDto.getFileNameForS3());
        File file = Path.of(path, messageDto.getFileNameForS3()).toFile();
        try (var outputStream = new FileOutputStream(file)) {
            outputStream.write(messageDto.getContent());
        } catch (IOException e) {
            log.error("Ошибка при записи файла {} кэш", messageDto.getFileNameForS3());
            throw new NoIdException("Ошибка при записи в кеш");
        }
    }

    /**
     * Очистка кеша
     */
    public void cleanFileCache() { // todo сделать тест
        try {
            Files.list(Path.of("uisbks/files/"))
                    .forEach(it -> {
                        try {
                            Files.delete(it);
                        } catch (IOException e) {
                            log.error("Ошибка при очистки кеша");
                        }
                    });
        } catch (IOException e) {
            log.error("Ошибка при считывании списка файлов для их очистки");
        }
    }
}
