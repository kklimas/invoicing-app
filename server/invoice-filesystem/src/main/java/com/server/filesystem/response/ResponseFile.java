package com.server.filesystem.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseFile {
    private String name;
    private String url;
    private long size;
}
