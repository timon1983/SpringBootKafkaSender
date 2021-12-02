package com.example.sbks.repository;

import java.io.File;


public interface S3Repository {

    void upload(File file, String fileName);

    void delete(String fileName);

    void getByName(String fileName);

}
