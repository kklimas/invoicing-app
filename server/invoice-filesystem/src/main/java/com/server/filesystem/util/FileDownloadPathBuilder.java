package com.server.filesystem.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDownloadPathBuilder {
    public static String build(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString()
                + "/api/filesystem/%d".formatted(id);
    }
}
