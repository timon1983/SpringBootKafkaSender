package com.example.SpringBootKafkaSender.controller;

import com.example.SpringBootKafkaSender.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/create")
public class MessageController {

    private final static Logger log = LogManager.getLogger(MessageController.class);

    @GetMapping("")
    public String getCreatePage() {
        return "message-insert-form";
    }

    /**
     * Получение сообщения от клиента
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping("")
    public String createMassage(HttpServletRequest request) throws ServletException, IOException {
        log.info("Receive message");
        Message message = Message.builder()
                .title(request.getParameter("title"))
                .size(request.getPart("file").getSize())
                .dateOfCreate(LocalDate.now())
                .author(request.getParameter("author"))
                .contentType(request.getPart("file").getContentType())
                .build();

        System.out.println();

        return "message-insert-form";
    }
}
