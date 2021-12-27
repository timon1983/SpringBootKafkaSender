package com.example.sbks.repository;

import com.example.sbks.model.Message;
import com.example.sbks.model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    MessageRepository messageRepository;


//    @BeforeEach
//    void getMessage() {
//
//    }

    @Test
    void findByOriginFileName_ShouldReturnOptionalOfMessage() {
        Message message = Message.builder()
                .id(1L)
                .originFileName("abc.txt")
                .fileNameForS3("1234.txt")
                .author("timon")
                .title("file")
                .dateOfCreate(LocalDateTime.now())
                .size(45L)
                .contentType("txt")
                .status(Status.UPLOAD)
                .build();
        messageRepository.save(message);

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
    void deleteAllByStatus_ShouldDeleteAllMessageByStatus() {
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.flush();
        messageRepository.deleteAllByStatus(Status.UPLOAD);
        List<Message> uploadMessages = messageRepository.findAllByStatus(Status.UPLOAD);
        assertNotNull(uploadMessages);
        assertEquals(uploadMessages, Collections.emptyList());
    }

    @Test
    void findAllByStatus_ShouldReturnListMessagesByStatus() {
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.flush();
        List<Message> uploadMessages = messageRepository.findAllByStatus(Status.UPLOAD);
        assertNotNull(uploadMessages);
        assertEquals(uploadMessages.size(), 3);
        assertEquals(uploadMessages.get(2).getStatus(), Status.UPLOAD);
    }

    @Test
    void findAllFileNameForS3ByStatus_ShouldReturnListOfNames() {
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.persist(message);
//        entityManager.flush();
        List<String> fileNames = messageRepository.findAllFileNameForS3ByStatus(Status.UPLOAD);
        assertNotNull(fileNames);
        assertEquals(fileNames.size(), 3);
        //assertEquals(fileNames.get(2), message.getFileNameForS3());
    }
}