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

    // todo в дтошках желательно использовать обертки, а не примитыивы, по умолчанию id = 0
    private long id;
    private String title;
    // todo в дтошках желательно использовать обертки,
    private long size;
    private LocalDate dateOfCreate;
    private LocalTime timeOfCreate;
    private String author;
    private String originFileName;
    private String fileNameForS3;
    private String contentType;
}
