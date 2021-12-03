package com.example.uisbks.controller;

import com.example.awsS3.service.ServiceS3;
import com.example.uisbks.dto.MessageDTO;
import com.example.uisbks.service.FileHandling;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ServiceS3 serviceS3;
    private final FileHandling fileHandling;

    @GetMapping
    public String getCreatePage() {

        return "message-insert-form";
    }

    @PostMapping
    public String createMessage(MultipartHttpServletRequest request) throws ServletException, IOException, URISyntaxException {
        log.info("Получение сообщения от клиента");
        MultipartFile multipartFile = request.getFile("file");
        File file = fileHandling.convertMultiPartFileToFile(Objects.requireNonNull(multipartFile));
        serviceS3.upload(file);
        MessageDTO message = MessageDTO.builder()
                .title(request.getParameter("title"))
                .size(request.getPart("file").getSize())
                .dateOfCreate(LocalDate.now())
                .timeOfCreate(LocalTime.now().withNano(0))
                .author(request.getParameter("author"))
                .originFileName(multipartFile.getOriginalFilename())
                .fileNameForS3(file.getName())
                .contentType(request.getPart("file").getContentType())
                .build();

        String url = "http://localhost:8085/api/sdk/create";
        URI uri = new URI(url);
        HttpEntity<MessageDTO> messageRequest = new HttpEntity<>(message);
        MessageDTO savedMessage = restTemplate.postForObject(uri, messageRequest, MessageDTO.class);
        System.out.println(savedMessage);
        return "message-insert-form";
    }
}
