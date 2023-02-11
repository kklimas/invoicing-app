package com.server.invoice.controller;

import com.server.invoice.dto.InvoiceStatusDTO;
import com.server.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invoicing")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("{id}")
    public Mono<InvoiceStatusDTO> checkInvoice(@PathVariable Long id) {
        return Mono.just(invoiceService.checkInvoice(id));
    }

    @PostMapping("{id}")
    public Mono<Void> invoice(@PathVariable Long id) throws IOException {
        return invoiceService.invoice(id);
    }
}
