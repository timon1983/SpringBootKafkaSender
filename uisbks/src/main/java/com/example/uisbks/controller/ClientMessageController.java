package com.example.uisbks.controller;

import com.example.awsS3.service.ServiceS3;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.service.FileHandling;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/create")
@RequiredArgsConstructor
public class ClientMessageController {

    private final static Logger log = LogManager.getLogger(ClientMessageController.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final FileHandling fileHandling;
    private final ServiceS3 serviceS3;

    @GetMapping
    public String getCreatePage() {

        return "message-insert-form";
    }

    @PostMapping
    public String createMessage(MultipartHttpServletRequest request) throws ServletException, IOException, URISyntaxException {
        log.info("Получение сообщения от клиента");
        MultipartFile multipartFile = request.getFile("file");
        File file = fileHandling.convertMultiPartFileToFile(Objects.requireNonNull(multipartFile));
        log.info("Запись файла {} в хранилище S3", file.getName());
        serviceS3.upload(file);
        DTOMessage message = DTOMessage.builder()
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
        HttpEntity<DTOMessage> messageRequest = new HttpEntity<>(message);
        log.info("Отправка данных по файлу {} в БД", file.getName());
        DTOMessage dtoMessage = restTemplate.postForObject(uri, messageRequest, DTOMessage.class);
        System.out.println(dtoMessage);
        return "message-insert-form";
    }
    @GetMapping("/files")
    public String getAllFiles(Model model){
        String url = "http://localhost:8085/api/sdk/files";
        List dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
        return "filesj";
    }

    @GetMapping("/file-delete/{id}")
    public String deleteFileById(@PathVariable long id){
        String url = "http://localhost:8085/api/sdk/files";
        String fileName = restTemplate.postForObject(url, id, String.class);
        serviceS3.delete(fileName);
        return "redirect:/files";
    }

    @GetMapping("open-file")
    public String openFile()

}
