package com.example.uisbks.dtomodel;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
}
