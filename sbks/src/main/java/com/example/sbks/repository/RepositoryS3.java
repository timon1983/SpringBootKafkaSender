package com.example.sbks.repository;

import java.io.File;
import java.io.IOException;

public interface RepositoryS3 {

    void save(File file, String fileName);

    void delete(String fileName);

    File download(String fileName) throws IOException;
}
