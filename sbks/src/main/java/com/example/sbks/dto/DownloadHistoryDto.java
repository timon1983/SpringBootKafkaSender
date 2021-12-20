package com.example.sbks.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DownloadHistoryDto {
    private Long id;
    private String fileName;
    private LocalDateTime dateOfDownload;
    private String ipUser;
}
