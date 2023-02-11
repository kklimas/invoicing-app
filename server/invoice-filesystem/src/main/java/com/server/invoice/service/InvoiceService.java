package com.server.invoice.service;

import com.server.enums.OperationStatus;
import com.server.event.EventService;
import com.server.event.db.model.Event;
import com.server.event.enums.EventProcessingState;
import com.server.event.mapper.EventToDTOMapper;
import com.server.filesystem.service.FileSystemService;
import com.server.invoice.dto.InvoiceStatusDTO;
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

    public InvoiceStatusDTO checkInvoice(Long id) {
        var events = eventService.findEventsByEventId(id);
        if (events.isEmpty()) {
            return givenInvoiceStatusResponse(OperationStatus.NOT_STARTED);
        }
        if (events.stream().allMatch(e -> EventProcessingState.PROCESSING != e.getEventState())) {
            var failedEvents = events.stream()
                    .filter(s -> EventProcessingState.FAILED == s.getEventState())
                    .toList();
            if (!failedEvents.isEmpty()) {
                return failedEvents.size() == events.size()
                        ? givenInvoiceStatusResponse(OperationStatus.FAILED, failedEvents)
                        : givenInvoiceStatusResponse(OperationStatus.PARTIAL, failedEvents);
            }
            return givenInvoiceStatusResponse(OperationStatus.DONE);
        }

        return givenInvoiceStatusResponse(OperationStatus.PROCESSING);
    }

    public Mono<Void> invoice(Long id) throws IOException {
        var events = fileSystemService.saveEventsFromFile(id);
        kafkaService.sendEvents(events);
        return Mono.empty();
    }

    private InvoiceStatusDTO givenInvoiceStatusResponse(OperationStatus status) {
        return InvoiceStatusDTO.builder()
                .invoiceStatus(status)
                .build();
    }

    private InvoiceStatusDTO givenInvoiceStatusResponse(OperationStatus status, List<Event> failedEvents) {
        var invoiceStatus = givenInvoiceStatusResponse(status);
        if (failedEvents != null) {
            invoiceStatus.setFailedEvents(failedEvents.stream()
                    .map(EventToDTOMapper::map)
                    .toList());
        }
        return invoiceStatus;
    }
}
