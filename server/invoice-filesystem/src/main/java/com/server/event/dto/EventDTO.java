package com.server.event.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventDTO {
    private Long id;
    private Long accountId;
    private Long cash;
}
