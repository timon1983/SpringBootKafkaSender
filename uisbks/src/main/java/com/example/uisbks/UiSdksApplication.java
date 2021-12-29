package com.example.uisbks;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Setter
@Getter
public class UiSdksApplication {
    private String token;
    public static void main(String[] args) {
        SpringApplication.run(UiSdksApplication.class, args);
    }

//    @Bean("token")
//    public String getToken(){
//        return token;
//    }
}
