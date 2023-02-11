package com.server.kafka.service;

import com.server.event.dto.EventDTO;
import com.server.event.db.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.server.event.mapper.EventToDTOMapper.map;

@Service
@RequiredArgsConstructor
public class KafkaEventSender {

    private final KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Value("${kafka.input_topic}")
    private String inputTopic;

    public void sendEvents(List<Event> events) {
        events.forEach(e -> {
            var message = MessageBuilder
                    .withPayload(map(e))
                    .setHeader(KafkaHeaders.TOPIC, inputTopic)
                    .build();
            kafkaTemplate.send(message);
        });
    }
}
