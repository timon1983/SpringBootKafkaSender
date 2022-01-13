package com.example.kafka.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTOKafkaMessage {

    private Long id;
    private String title;
    private Long size;
    private LocalDate dateOfCreate;
    private LocalTime timeOfCreate;
    private String author;
    private String originFileName;
    private String fileNameForS3;
    private String contentType;
}
