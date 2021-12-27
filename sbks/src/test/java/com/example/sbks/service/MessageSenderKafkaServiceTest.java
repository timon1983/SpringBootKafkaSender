package com.example.sbks.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageSenderKafkaServiceTest {

    @Mock
    MessageSenderKafkaService messageSenderKafkaService;

    @Test
    void check_sendMessage() throws URISyntaxException {
        messageSenderKafkaService.sendMessage("abc.txt");
        verify(messageSenderKafkaService).sendMessage("abc.txt");
    }
}