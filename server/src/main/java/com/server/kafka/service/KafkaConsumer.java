package com.server.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private final String INPUT_TOPIC = "input";
    private final String GROUP_ID = "group_id";

    @KafkaListener(topics = INPUT_TOPIC, groupId = GROUP_ID)
    public void consume(String message) {
        logger.info("Received message %s".formatted(message));
    }

}
