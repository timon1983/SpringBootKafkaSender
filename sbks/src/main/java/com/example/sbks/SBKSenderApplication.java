package com.example.sbks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.awsS3", "com.example.sbks" })
public class SBKSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SBKSenderApplication.class, args);
	}

}
