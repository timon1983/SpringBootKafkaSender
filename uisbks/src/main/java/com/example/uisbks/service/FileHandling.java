package com.example.uisbks.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileHandling {

    private final static Logger log = LogManager.getLogger(FileHandling.class);

    /**
     * Конвертирование из MultipartFile в File
     */
    public File convertMultiPartFileToFile(String fileName, byte[] multipartFile) {
        File convFile = new File(getNewFileName(fileName));
        try (var fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile);
        } catch (IOException e) {
            log.error("Ошибка при конвертировании файла", e);
        }
        return convFile;
    }

    /**
     * Получение нового имени для файла
     */
    private String getNewFileName(String originalFilename) {
        return String.join(
                "",
                originalFilename.substring(0, originalFilename.lastIndexOf(".")),
                "-",
                String.valueOf(System.currentTimeMillis()),
                originalFilename.substring(originalFilename.lastIndexOf(".")));
    }
}

