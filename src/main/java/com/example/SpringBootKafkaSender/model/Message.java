package com.example.SpringBootKafkaSender.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder(toBuilder = true)
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "size", nullable = false)
    private long size;
    @Column(name = "date_create", nullable = false)
    private LocalDate dateOfCreate;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "type", nullable = false)
    private String contentType;
}
