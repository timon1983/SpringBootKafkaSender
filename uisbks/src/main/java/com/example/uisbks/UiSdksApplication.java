package com.example.uisbks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example"})
public class UiSdksApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiSdksApplication.class, args);
    }

}
