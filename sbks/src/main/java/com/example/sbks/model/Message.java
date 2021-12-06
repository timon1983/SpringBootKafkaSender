package com.example.sbks.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
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
    @Column(name = "time_create", nullable = false)
    private LocalTime timeOfCreate;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "origin_file_name", nullable = false)
    private String originFileName;
    @Column(name = "file_name_for_s3", nullable = false)
    private String fileNameForS3;
    @Column(name = "type", nullable = false)
    private String contentType;
}
