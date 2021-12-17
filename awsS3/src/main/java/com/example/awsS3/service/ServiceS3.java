package com.example.awsS3.service;

import java.io.File;
import java.io.IOException;

public interface ServiceS3 {

    void upload(File file);

    void delete(String fileName);

    File getFile(String fileName) throws IOException;

}
