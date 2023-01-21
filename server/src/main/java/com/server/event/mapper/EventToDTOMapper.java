package com.server.event.mapper;

import com.server.event.dto.EventDTO;
import com.server.event.model.Event;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventToDTOMapper {
    public static EventDTO map(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .accountId(event.getAccountId())
                .cash(event.getCash())
                .build();
    }
}
