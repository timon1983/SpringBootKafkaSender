package com.example.sbks.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DownloadClientInfo {
    private Long idFile;
    private String fileName;
    private String ipUser;
    private LocalDateTime dateOfDownload;
}
