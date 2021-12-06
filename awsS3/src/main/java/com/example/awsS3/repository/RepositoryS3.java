package com.example.awsS3.repository;

import java.io.File;

public interface RepositoryS3 {

    void save(File file, String fileName);
    void delete(String fileName);
}
