package com.example.sbks.repository;

import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    MessageRepository messageRepository;

    @Test
    void findByOriginFileName_ShouldReturnOptionalOfMessage() {
        Message message = Message.builder()
                .originFileName("abc.txt")
                .fileNameForS3("1234.txt")
                .author("timon")
                .title("file")
                .dateOfCreate(LocalDateTime.now())
                .size(45L)
                .contentType("txt")
                .status(Status.UPLOAD)
                .build();
        entityManager.persist(message);
        entityManager.flush();

        messageRepository.findByOriginFileName(message.getOriginFileName())
                .map(foundMessage -> {
                    assertEquals(foundMessage.getOriginFileName(), message.getOriginFileName());
                    assertEquals(foundMessage.getFileNameForS3(), message.getFileNameForS3());
                    assertEquals(foundMessage.getAuthor(), message.getAuthor());
                    assertEquals(foundMessage.getTitle(), message.getTitle());
                    assertEquals(foundMessage.getDateOfCreate(), message.getDateOfCreate());
                    assertEquals(foundMessage.getSize(), message.getSize());
                    assertEquals(foundMessage.getContentType(), message.getContentType());
                    assertEquals(foundMessage.getStatus(), message.getStatus());
                    return foundMessage;
                }).orElseThrow(() -> {
                    throw new NullPointerException();
                });
    }

    @Test
    void deleteAllByStatus() {
    }

    @Test
    void findAllByStatus() {
    }

    @Test
    void findAllFileNameForS3ByStatus() {
    }
}