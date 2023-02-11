package com.server.filesystem.service;

import com.server.event.EventService;
import com.server.event.db.model.Event;
import com.server.event.enums.EventProcessingState;
import com.server.exception.FileAlreadyInvoicedException;
import com.server.filesystem.db.model.StorageFile;
import com.server.filesystem.db.repository.StorageFileRepository;
import com.server.filesystem.util.CSVFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileSystemService {

    @Value("${spring.mvc.format.date-time}")
    private String formatPattern;

    private final StorageFileRepository storageFileRepository;
    private final EventService eventService;
    private final CSVFileService csvFileService;

    public List<StorageFile> getFiles() {
        return storageFileRepository.findAll();
    }

    public StorageFile getFile(Long id) {
        return storageFileRepository.findById(id).orElseThrow();
    }

    public Mono<Void> storeFile(MultipartFile file) throws IOException {
        var formatter = DateTimeFormatter.ofPattern(formatPattern);
        storageFileRepository.save(StorageFile.builder()
                .name(file.getName())
                .content(file.getBytes())
                .storageDate(LocalDateTime.now().format(formatter))
                .build());
        return Mono.empty();
    }

    public Mono<Void> removeFileById(Long id) {
        storageFileRepository.deleteById(id);
        return Mono.empty();
    }

    public List<Event> saveEventsFromFile(Long id) throws IOException {
        var events = eventService.findEventsByEventId(id);
        if (!canBeInvoiced(events)) {
            throw new FileAlreadyInvoicedException("File is invoiced or still invoicing.");
        }
        var file = getFile(id);
        var stream = new ByteArrayInputStream(file.getContent());
        var eventsToSave = csvFileService.getEventsFromFile(stream, file);
        storageFileRepository.save(file);
        return saveEvents(eventsToSave);
    }

    private List<Event> saveEvents(List<Event> events) {
        return eventService.saveProcessingEvents(events);
    }

    private boolean canBeInvoiced(List<Event> events) {
        return events.isEmpty()
                || events.stream().allMatch(e -> EventProcessingState.NOT_STARTED == e.getEventState());
    }
}
