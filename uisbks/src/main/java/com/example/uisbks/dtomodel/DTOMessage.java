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
    private Long id;
    private String title;
    private Long size;
    private LocalDateTime dateOfCreate;
    private String author;
    private String originFileName;
    private String fileNameForS3;
    private String contentType;
    private LocalDateTime dateOfDelete;
    private byte[] content;
}
