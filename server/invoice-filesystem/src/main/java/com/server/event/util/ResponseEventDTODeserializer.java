package com.server.event.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.event.dto.ResponseEventDTO;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

@NoArgsConstructor
public class ResponseEventDTODeserializer implements Deserializer<ResponseEventDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public ResponseEventDTO deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, ResponseEventDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEventDTO deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
