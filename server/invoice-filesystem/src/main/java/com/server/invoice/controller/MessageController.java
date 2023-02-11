package com.server.invoice.controller;

import com.server.enums.OperationStatus;
import com.server.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/topic")
public class MessageController {

    private final InvoiceService invoiceService;

//    @MessageMapping("/invoice")
//    @SendTo("/topic/messages")
//    public OperationStatus getInvoiceStatus(@RequestParam  Long id) {
//        return invoiceService.checkInvoice(id);
//    }
}
