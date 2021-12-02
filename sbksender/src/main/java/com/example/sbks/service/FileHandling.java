package com.example.sbks.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Component
public class FileHandling {

    private final static Logger log = LogManager.getLogger(FileHandling.class);

    /**
     * Конвертирование из MultipartFile в File
     */
    public File convertMultiPartFileToFile(MultipartFile multipartFile) {
        var fileName = getNewFileName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        File convFile = new File(fileName);
        try (var fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Ошибка при конвертировании файла", e);
        }
        return convFile;
    }

    private String getNewFileName(String originalFilename) {
        return String.join(
                "",
                originalFilename.substring(0, originalFilename.lastIndexOf(".")),
                "-",
                String.valueOf(System.currentTimeMillis()),
                originalFilename.substring(originalFilename.lastIndexOf(".")));
    }
}

