package com.example.uisbks.dtomodel;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTODownloadClientInfo {
    private Long idFile;
    private String fileName;
    private String ipUser;
    private LocalDateTime dateOfDownload;
}
