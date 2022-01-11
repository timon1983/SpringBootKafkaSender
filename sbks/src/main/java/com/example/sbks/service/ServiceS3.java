package com.example.sbks.service;

import java.io.File;
import java.io.IOException;

/**
 * Сервис для работы с хранилищем AWS S3
 */
public interface ServiceS3 {

    /**
     * Загрузка файла в хранилище
     */
    void upload(File file);

    /**
     * Удаление файла из хранилища
     */
    void delete(String fileName);

    /**
     * загрузка файла из хранилища
     */
    File getFile(String fileName) throws IOException;

}
