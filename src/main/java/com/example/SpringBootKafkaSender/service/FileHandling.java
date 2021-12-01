package com.example.SpringBootKafkaSender.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Component
public class FileHandling {

    private final static Logger log = LogManager.getLogger(FileHandling.class);

    /**
     * Конвертирование из MultipartFile в File
     *
     *
     *
     */
    public File convertMultiPartFileToFile(MultipartFile multipartFile) {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Ошибка при конвертировании файла");
            e.printStackTrace();
        }
        return renameFile(convFile);
    }

    /**
     * Переименование файла для уникальности
     *
     *
     *
     */
    public File renameFile(File file) {
        StringBuilder fileName = new StringBuilder(file.getName());
        int index = fileName.lastIndexOf(".");
        String suffix = String.valueOf(new Date().getTime());
        String newFileName = fileName.insert(index, "-" + suffix).toString();
        File newFile = new File(newFileName);
        if(file.renameTo(newFile)) {
            log.info("Файл переименован");
            return newFile;
        }else{
            log.error("Файл не переименован");
            return file;
        }
    }
}

