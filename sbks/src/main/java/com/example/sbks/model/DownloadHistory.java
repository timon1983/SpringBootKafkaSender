package com.example.sbks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder(toBuilder = true)
@Table(name = "download_history")
@AllArgsConstructor
@NoArgsConstructor
public class DownloadHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "date_create", nullable = false)
    private LocalDateTime dateOfDownload;

    @Column(name = "ip_user", nullable = false)
    private String ipUser;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonIgnore
    private Message message;
}
