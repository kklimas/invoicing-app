package com.server.invoice.service;

import com.server.event.EventService;
import com.server.enums.OperationStatus;
import com.server.event.model.Event;
import com.server.filesystem.service.FileSystemService;
import com.server.kafka.service.KafkaEventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final FileSystemService fileSystemService;
    private final EventService eventService;
    private final KafkaEventSender kafkaService;

    public OperationStatus getInvoiceStatus(Long id) {
        var events = eventService.findAllByFileId(id);
        if (events.isEmpty()) {
            return OperationStatus.NOT_STARTED;
        }
        var eventStatuses = events.stream()
                .map(Event::getEventStatus)
                .toList();
        if (eventStatuses.stream().allMatch(status -> OperationStatus.PROCESSING != status)) {
            if (eventStatuses.contains(OperationStatus.FAILED)) {
                return OperationStatus.PARTIAL;
            }
            return OperationStatus.DONE;
        }

        return OperationStatus.PROCESSING;
    }

    public Mono<Void> invoice(Long id) throws IOException {
        var events = fileSystemService.getEventsFromFile(id);
        events = saveEvents(events);
        kafkaService.sendEvents(events);

        return Mono.empty();
    }

    private List<Event> saveEvents(List<Event> events) {
        events.forEach(e -> e.setEventStatus(OperationStatus.PROCESSING));
        return eventService.saveAll(events);
    }
}
