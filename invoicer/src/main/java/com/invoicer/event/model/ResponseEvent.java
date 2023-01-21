package com.invoicer.event.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEvent {
    private Long id;
    private String message;
}
