package com.example.SpringBootKafkaSender.model;

import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.util.Date;

@Entity
@Data
@Table(name = "messages")
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
    private Date dateOfCreate;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "content", nullable = false)
    File content;


}
