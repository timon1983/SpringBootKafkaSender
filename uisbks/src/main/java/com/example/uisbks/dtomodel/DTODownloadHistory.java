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
    private long id;
    private String ipUser;
    private LocalDateTime dateOfDownload;
}
