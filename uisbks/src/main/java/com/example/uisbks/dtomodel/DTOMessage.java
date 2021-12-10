package com.example.uisbks.dtomodel;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTOMessage {

    private long id;
    private String title;
    private long size;
    private LocalDateTime dateOfCreate;
    private String author;
    private String originFileName;
    private String fileNameForS3;
    private String contentType;
    private LocalDateTime dateOfDelete;
    private byte[] content;
}
