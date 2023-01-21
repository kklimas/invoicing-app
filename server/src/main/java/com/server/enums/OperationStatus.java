package com.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OperationStatus {
    NOT_STARTED("Not started yet"),
    PROCESSING("Processing"),
    FAILED("Failed"),
    PARTIAL("Some of events failed"),
    DONE("Done");

    private final String value;
}
