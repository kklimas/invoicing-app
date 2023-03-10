package com.server.filesystem.controller;

import com.server.filesystem.mapper.StorageToResponseFileMapper;
import com.server.filesystem.response.ResponseFile;
import com.server.filesystem.service.FileSystemService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filesystem")
@CrossOrigin
public class FileSystemController {

    private final FileSystemService fileSystemService;

    @ApiOperation("List already saved files.")
    @GetMapping
    public List<ResponseFile> getFiles() {
        return fileSystemService.getFiles().stream()
                .map(StorageToResponseFileMapper::map)
                .toList();
    }

    @ApiOperation("Download csv file of given id.")
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        var file = fileSystemService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file.getContent());
    }

    @ApiOperation("Upload file.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public Mono<Void> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileSystemService.storeFile(file);
    }

    @ApiOperation("Delete file of given id.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public Mono<Void> removeFile(@PathVariable("id") Long id) {
        return fileSystemService.removeFileById(id);
    }
}
