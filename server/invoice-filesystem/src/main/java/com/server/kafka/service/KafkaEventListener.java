package com.server.kafka.service;

import com.server.event.EventService;
import com.server.event.dto.ResponseEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventListener {

    private final EventService eventService;

    @KafkaListener(topics = "finished_topic", groupId = "group")
    public void consumeFinishedEvent(ResponseEventDTO event) {
        eventService.setDoneStatus(event);
    }

    @KafkaListener(topics = "failed_topic", groupId = "group")
    public void consumeFailEvent(ResponseEventDTO event) {
        eventService.setFailedStatus(event);
    }
}
