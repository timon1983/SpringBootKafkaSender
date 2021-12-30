package com.example.uisbks.dtomodel;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DTOInfoModelClient {
    private String info;
    private Object object;
    private Boolean isError;
}
