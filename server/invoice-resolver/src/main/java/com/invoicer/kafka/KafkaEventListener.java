package com.invoicer.kafka;

import com.invoicer.InvoiceService;
import com.invoicer.event.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
@RequiredArgsConstructor
public class KafkaEventListener {

    private final InvoiceService invoiceService;

    @KafkaListener(topics = "input_topic", groupId = "group")
    public void consume(Event event) {
        invoiceService.processEvent(event);
    }
}
