package com.server.filesystem.service;

import com.server.filesystem.db.model.StorageFile;
import com.server.filesystem.db.repository.StorageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileSystemService {

    private final StorageFileRepository csvFileRepository;
    public List<StorageFile> getFiles() {
        return csvFileRepository.findAll();
    }

    public StorageFile getFile(Long id) {
        //todo custom exception
        return csvFileRepository.findById(id).orElseThrow();
    }

    public Mono<Void> storeFile(MultipartFile file) throws IOException {
        csvFileRepository.save(StorageFile.builder()
                .name(file.getName())
                .content(file.getBytes())
                .build());
        return Mono.empty();
    }

    public Mono<Void> removeFileById(Long id) {
        csvFileRepository.deleteById(id);
        return Mono.empty();
    }
}
