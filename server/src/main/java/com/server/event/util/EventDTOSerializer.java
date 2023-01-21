package com.server.event.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.event.dto.EventDTO;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@NoArgsConstructor
public class EventDTOSerializer implements Serializer<EventDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @SneakyThrows
    @Override
    public byte[] serialize(String topic, EventDTO data) {
        return objectMapper.writeValueAsBytes(data);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, EventDTO data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
