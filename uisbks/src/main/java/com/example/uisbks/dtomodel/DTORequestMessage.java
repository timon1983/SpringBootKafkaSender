package com.example.uisbks.dtomodel;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTORequestMessage {
    private Long id;
    private String title;
    private String author;
    private MultipartFile file;

}
