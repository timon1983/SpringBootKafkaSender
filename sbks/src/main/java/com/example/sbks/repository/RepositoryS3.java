package com.example.sbks.repository;

import java.io.File;
import java.io.IOException;

/**
 * Интерфейс для взамодействия с хранилищем S3
 */
public interface RepositoryS3 {

    /**
     * Запись файла
     */
    void save(File file, String fileName);

    /**
     * Удаление файла
     */
    void delete(String fileName);

    /**
     * Скачивание файла
     */
    File download(String fileName) throws IOException;
}
