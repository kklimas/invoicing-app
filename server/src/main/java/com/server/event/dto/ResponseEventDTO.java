package com.server.event.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEventDTO {
    private Long id;
    private String message;
}
