package com.example.awsS3.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.example.awsS3.configuration.S3Configurer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.File;


@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryS3Impl implements RepositoryS3 {

    private final static Logger log = LogManager.getLogger(RepositoryS3Impl.class);
    private final String bucket = "springbootkafkasender";
    private final AmazonS3 amazonS3 = S3Configurer.getAmazonS3Client();

    @Override
    public void save(File file, String fileName) {
        log.info("Загрузка файла {} в хранилище S3", fileName);
        amazonS3.putObject(bucket, fileName, file);

    }

    @Override
    public void delete(String fileName) {
        amazonS3.deleteObject(bucket, fileName);
    }
}
