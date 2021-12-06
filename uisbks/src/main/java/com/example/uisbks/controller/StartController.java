package com.example.uisbks.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class StartController {

    private final static Logger log = LogManager.getLogger(StartController.class);

    /**
     * Переход на стартовую страницу приложения
     */
    @GetMapping
    public String startProject() {
        log.info("Start project");
        return "start-page";
    }
}
