package com.example.sbks.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class InfoDto {
    private String info;
    @Builder.Default
    private Boolean isError = false;
}
