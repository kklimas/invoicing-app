package com.server.filesystem.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseFile {
    private Long id;
    private String name;
    private String storageDate;
    private String url;
    private long size;
}
