package com.example.uisbks.service;

import com.example.awsS3.service.ServiceS3;
import com.example.uisbks.dtomodel.DTOMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientMessageServiceImpl implements ClientMessageService {

    private final ServiceS3 serviceS3;

    @Override
    public void saveMessageToS3(File file) {
        serviceS3.upload(file);
    }

    @Override
    public void cleanAllDeletedMessageFromS3(List<DTOMessage> dtoMessages) {
        dtoMessages.forEach(x -> serviceS3.delete(x.getFileNameForS3()));
    }

    @Override
    public void deleteFromS3ByName(String name) {
        serviceS3.delete(name);
    }
}
