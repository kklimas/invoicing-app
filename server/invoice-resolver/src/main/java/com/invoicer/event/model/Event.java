package com.invoicer.event.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private Long id;
    private Long accountId;
    private Long cash;
}
