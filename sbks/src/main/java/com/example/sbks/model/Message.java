package com.example.sbks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime dateOfCreate;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "origin_file_name", nullable = false)
    private String originFileName;
    @Column(name = "file_name_for_s3", nullable = false, unique = true)
    private String fileNameForS3;
    @Column(name = "type", nullable = false)
    private String contentType;
    @Column(name = "date_delete")
    private LocalDateTime dateOfDelete;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DownloadHistory> downloadHistories;
}
