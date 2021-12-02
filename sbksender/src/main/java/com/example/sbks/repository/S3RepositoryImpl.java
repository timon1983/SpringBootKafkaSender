package com.example.sbks.repository;

import com.amazonaws.services.s3.AmazonS3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public class S3RepositoryImpl implements S3Repository{

    private final static Logger log = LogManager.getLogger(S3RepositoryImpl.class);

    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public S3RepositoryImpl(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Override
    public void upload(File file, String fileName) {
        s3client.putObject(bucketName,fileName,file);
    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public void getByName(String fileName) {

    }
}
