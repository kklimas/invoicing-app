package com.server.filesystem.mapper;

import com.server.filesystem.response.ResponseFile;
import com.server.filesystem.db.model.StorageFile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.server.filesystem.util.FileDownloadPathBuilder.build;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageToResponseFIleMapper {
    public static ResponseFile map(StorageFile csvFile, Long id) {
        var url = build(id);
        return ResponseFile.builder()
                .name(csvFile.getName())
                .url(url)
                .size(csvFile.getContent().length)
                .build();
    }
}
