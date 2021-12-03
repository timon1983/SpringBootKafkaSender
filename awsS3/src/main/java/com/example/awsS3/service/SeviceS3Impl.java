package com.example.awsS3.service;

import com.example.awsS3.repository.RepositoryS3;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class SeviceS3Impl implements ServiceS3 {

    private final static Logger log = LogManager.getLogger(SeviceS3Impl.class);
    private final RepositoryS3 repositoryS3;


    @Override
    public void upload(File file) {
        var fileName = file.getName();
        repositoryS3.save(file, fileName);
        try {
            Files.deleteIfExists(Path.of(fileName));
            log.info("Временный файл удален");
        } catch (IOException e) {
            log.error("Временный файл не удален");
        }
    }
}
