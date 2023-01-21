package com.server.filesystem.controller;

import com.server.filesystem.service.FileSystemService;
import com.server.filesystem.response.ResponseFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

import static com.server.filesystem.mapper.StorageToResponseFIleMapper.map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filesystem")
public class FileSystemController {

    private final FileSystemService fileSystemService;

    @GetMapping
    public List<ResponseFile> getFiles() {
        return fileSystemService.getFiles().stream()
                .map(file ->
                        map(file, file.getId())
                )
                .toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        var file = fileSystemService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file.getContent());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public Mono<Void> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileSystemService.storeFile(file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public Mono<Void> removeFile(@RequestParam("id") Long id) {
        return fileSystemService.removeFileById(id);
    }
}
