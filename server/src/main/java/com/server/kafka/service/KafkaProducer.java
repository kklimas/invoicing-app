package com.server.kafka.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.input_topic}")
    private String INPUT_TOPIC;

    public void sendMessage(String message) {
        logger.info("Sending message to topic %s".formatted(INPUT_TOPIC));
        this.kafkaTemplate.send(INPUT_TOPIC, message);
    }
}
