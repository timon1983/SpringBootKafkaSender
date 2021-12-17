package com.example.uisbks.service;

import com.example.uisbks.dtomodel.DTOInfoModelClient;
import com.example.uisbks.dtomodel.DTOMessage;
import com.example.uisbks.exception.NoIdException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageDeletedService {

    private final RestTemplate restTemplate;
    private final ClientDTOMessageService clientDTOMessageService;

    /**
     * Метод для выполнения операций с файлами в корзине(полное удаление, восстановление)
     */
    public String doOperationWithDeletedFile(String urlEndPoint, String action, HttpServletRequest request, Logger log) {
        Long id = Long.parseLong(request.getParameter("id"));
        DTOInfoModelClient dtoInfoModelClient = restTemplate.postForObject(clientDTOMessageService.getUrl(urlEndPoint),
                id, DTOInfoModelClient.class);
        if (dtoInfoModelClient != null && dtoInfoModelClient.getIsError()) {
            log.error(dtoInfoModelClient.getInfo());
            throw new NoIdException(dtoInfoModelClient.getInfo());
        }
        log.info("Файл c id={} {}", id, action);
        return "redirect:/deleted";
    }

    /**
     * Метод для выполнения оперций над списком файлов в корзине(получение списка, очистка корзины)
     */
    public void doOperationWithListOfDeletedFile(Model model, String urlEndPoint) {
        List<DTOMessage> dtoMessages = restTemplate.getForObject(clientDTOMessageService.getUrl(urlEndPoint), List.class);
        model.addAttribute("listOfFiles", dtoMessages);
    }

}
