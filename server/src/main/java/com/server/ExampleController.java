package com.server;

import com.server.kafka.service.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ExampleController {
    private final KafkaProducer producer;

    @PostMapping
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producer.sendMessage("Hello kafka!");
    }

}
