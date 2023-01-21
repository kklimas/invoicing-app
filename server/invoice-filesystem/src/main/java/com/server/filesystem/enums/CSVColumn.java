package com.server.filesystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CSVColumn {
    ACCOUNT_ID("accountId"),
    CASH("cash");

    private final String value;
}
