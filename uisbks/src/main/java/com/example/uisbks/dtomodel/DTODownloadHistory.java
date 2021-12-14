package com.example.uisbks.dtomodel;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTODownloadHistory {
    private Long id;
    private String fileName;
    private String ipUser;
    private LocalDateTime dateOfDownload;
}
