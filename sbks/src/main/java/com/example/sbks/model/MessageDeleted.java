package com.example.sbks.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Builder(toBuilder = true)
@Table(name = "messages_deleted")
@AllArgsConstructor
@NoArgsConstructor
public class MessageDeleted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "size", nullable = false)
    private long size;
    @Column(name = "date_delete", nullable = false)
    private LocalDate dateOfDelete;
    @Column(name = "time_delete", nullable = false)
    private LocalTime timeOfDelete;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "origin_file_name", nullable = false)
    private String originFileName;
    @Column(name = "type", nullable = false)
    private String contentType;
}
