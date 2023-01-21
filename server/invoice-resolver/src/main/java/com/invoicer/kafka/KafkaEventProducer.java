package com.invoicer.kafka;

import com.invoicer.event.model.ResponseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaTemplate<String, ResponseEvent> kafkaTemplate;

    @Value("${kafka.finished_topic}")
    private String finishedTopic;

    @Value("${kafka.failed_topic}")
    private String failedTopic;

    public void sendFinishedEvent(ResponseEvent event) {
        var message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, finishedTopic)
                .build();
        kafkaTemplate.send(message);
    }

    public void sendFailEvent(ResponseEvent event) {
        var message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, failedTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
