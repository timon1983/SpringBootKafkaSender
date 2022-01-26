package com.example.sbks.repository;

import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;
    Message message1;
    Message message2;


    @BeforeEach
    void getMessage() {
        message1 = Message.builder()
                .originFileName("abc.txt")
                .fileNameForS3("1234.txt")
                .author("timon")
                .title("file")
                .dateOfCreate(LocalDateTime.now())
                .size(45L)
                .contentType("txt")
                .status(Status.UPLOAD)
                .build();
        message2 = Message.builder()
                .originFileName("abcd.txt")
                .fileNameForS3("12345.txt")
                .author("timon83")
                .title("file2")
                .dateOfCreate(LocalDateTime.now())
                .size(48L)
                .contentType("txt")
                .status(Status.UPLOAD)
                .build();
    }

    @Test
    void findByOriginFileName_ShouldReturnOptionalOfMessage() {
        messageRepository.save(message1);

        var foundMessage = messageRepository.findByOriginFileName(message1.getOriginFileName()).get(0)
                .orElseThrow(NullPointerException::new);

        assertEquals(foundMessage.getOriginFileName(), message1.getOriginFileName());
        assertEquals(foundMessage.getFileNameForS3(), message1.getFileNameForS3());
        assertEquals(foundMessage.getAuthor(), message1.getAuthor());
        assertEquals(foundMessage.getTitle(), message1.getTitle());
        assertEquals(foundMessage.getDateOfCreate(), message1.getDateOfCreate());
        assertEquals(foundMessage.getSize(), message1.getSize());
        assertEquals(foundMessage.getContentType(), message1.getContentType());
        assertEquals(foundMessage.getStatus(), message1.getStatus());
    }

    @Test
    void deleteAllByStatus_ShouldDeleteAllMessageByStatus() {
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.deleteAllByStatus(Status.UPLOAD);
        List<Message> uploadMessages = messageRepository.findAllByStatus(Status.UPLOAD, PageRequest.of(0, 10));
        assertNotNull(uploadMessages);
        assertEquals(uploadMessages, Collections.emptyList());
    }

    @Test
    void findAllByStatus_ShouldReturnListMessagesByStatus() {
        messageRepository.save(message1);
        messageRepository.save(message2);
        List<Message> uploadMessages = messageRepository.findAllByStatus(Status.UPLOAD, PageRequest.of(0, 2));
        assertNotNull(uploadMessages);
        assertEquals(uploadMessages.size(), 2);
        assertEquals(uploadMessages.get(1).getStatus(), Status.UPLOAD);
    }

    @Test
    void findAllFileNameForS3ByStatus_ShouldReturnListOfNames() {
        messageRepository.save(message1);
        messageRepository.save(message2);
        List<String> fileNames = messageRepository.findAllFileNameForS3ByStatus(Status.UPLOAD,
                PageRequest.of(0, 10));
        assertNotNull(fileNames);
        assertEquals(fileNames.size(), 2);
    }
}