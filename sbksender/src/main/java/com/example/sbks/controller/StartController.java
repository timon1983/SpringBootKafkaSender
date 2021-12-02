package com.example.sbks.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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
