package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageDeletedService {

    private final RestTemplate restTemplate;

    /**
     * Метод для выполнения операций с файлами в корзине(полное удаление, восстановление)
     */
    public String doOperationWithDeletedFile(String urlEndPoint, String action, HttpServletRequest request, Logger log) {
        Long id = Long.parseLong(request.getParameter("id"));
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        String result = restTemplate.postForObject(url, id, String.class);
        if (result != null) {
            log.error(result);
            throw new NoIdException(result);
        }
        log.info("Файл c id={} {}", id, action);
        return "redirect:/deleted";
    }

    /**
     * Метод для выполнения оперций над списком файлов в корзине(получение списка, очистка корзины)
     */
    public void doOperationWithListOfDeletedFile(Model model, String urlEndPoint) {
        var url = String.format("http://localhost:8085/api/sdk/%s", urlEndPoint);
        List<DTOMessage> dtoMessages = restTemplate.getForObject(url, List.class);
        model.addAttribute("listOfFiles", dtoMessages);
    }

}
