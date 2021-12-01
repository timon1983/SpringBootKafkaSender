package com.example.SpringBootKafkaSender.controller;

import com.example.SpringBootKafkaSender.model.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Controller
@RequestMapping("/create")
public class MessageController {

    @GetMapping("")
    public String getCreatePage(){
        return "message-insert-form";
    }

    @PostMapping("")
    public ResponseEntity<File> createMassage(Test test){
        String path = test.getFile().getPath();
        System.out.println(path);
        return null;
    }
}
