package com.server.filesystem.mapper;

import com.server.filesystem.response.ResponseFile;
import com.server.filesystem.db.model.StorageFile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.server.filesystem.util.FileDownloadPathBuilder.build;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageToResponseFileMapper {
    public static ResponseFile map(StorageFile csvFile) {
        var url = build(csvFile.getId());
        return ResponseFile.builder()
                .id(csvFile.getId())
                .name(csvFile.getName())
                .storageDate(csvFile.getStorageDate())
                .url(url)
                .size(csvFile.getContent().length)
                .build();
    }
}
