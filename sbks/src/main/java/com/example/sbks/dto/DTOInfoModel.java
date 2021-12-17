package com.example.sbks.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTOInfoModel {
    private Long id;
    private String info;
    private Boolean isError;
}
