package com.example.awsS3.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Configurer {


    @Bean
    public static AmazonS3 getAmazonS3Client() {

        AWSCredentials credentials = new BasicAWSCredentials("AKIAXKA7I4RJVGT3NCV6",
                "f7dKxUg+23DB64INVI4oMI7U8reanNEs/3PrG2sE");
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName("eu-central-1"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
