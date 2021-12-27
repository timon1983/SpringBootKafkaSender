package com.example.sbks.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public class RepositoryS3Impl implements RepositoryS3 {

    private final static Logger log = LogManager.getLogger(RepositoryS3Impl.class);
    private final String bucket;
    private final AmazonS3 amazonS3;

    public RepositoryS3Impl(@Value("${aws.s3.bucket}")String bucket, AmazonS3 amazonS3) {
        this.bucket = bucket;
        this.amazonS3 = amazonS3;
    }

    @Override
    public void save(File file, String fileName) {
        log.info("Загрузка файла {} в хранилище S3", fileName);
        amazonS3.putObject(bucket, fileName, file);

    }

    @Override
    public void delete(String fileName) {
        amazonS3.deleteObject(bucket, fileName);

    }

    @Override
    public File download(String fileName) throws IOException {
        File file = new File(fileName);
        S3ObjectInputStream inputStream = amazonS3.getObject(bucket, fileName).getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, file);
        return file;
    }
}
