package com.server.filesystem.service;

import com.server.event.model.Event;
import com.server.filesystem.db.model.StorageFile;
import com.server.filesystem.db.repository.StorageFileRepository;
import com.server.filesystem.util.CSVFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileSystemService {

    private final StorageFileRepository storageFileRepository;
    private final CSVFileService csvFileService;

    public List<StorageFile> getFiles() {
        return storageFileRepository.findAll();
    }

    public StorageFile getFile(Long id) {
        return storageFileRepository.findById(id).orElseThrow();
    }

    public Mono<Void> storeFile(MultipartFile file) throws IOException {
        storageFileRepository.save(StorageFile.builder()
                .name(file.getName())
                .content(file.getBytes())
                .build());
        return Mono.empty();
    }

    public Mono<Void> removeFileById(Long id) {
        storageFileRepository.deleteById(id);
        return Mono.empty();
    }

    public List<Event> getEventsFromFile(Long id) throws IOException {
        var file = getFile(id);
        var stream = new ByteArrayInputStream(file.getContent());
        return csvFileService.getEventsFromFile(stream, id);
    }
}
