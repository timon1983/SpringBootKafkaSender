package com.example.awsS3.service;

import java.io.File;

public interface ServiceS3 {

    void upload(File file);
    void delete(String fileName);
}
