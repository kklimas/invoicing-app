package com.server.invoice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.server.enums.OperationStatus;
import com.server.event.dto.EventDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class InvoiceStatusDTO {
    private OperationStatus invoiceStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EventDTO> failedEvents;
}
