package com.invoicer;

import com.invoicer.account.model.Account;
import com.invoicer.account.AccountService;
import com.invoicer.event.model.Event;
import com.invoicer.event.model.ResponseEvent;
import com.invoicer.kafka.KafkaEventProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private static final String ERROR_MESSAGE = "Event data is invalid.";
    private static final String OK_MESSAGE = "Ok";

    private final Logger logger = LoggerFactory.getLogger(InvoiceService.class);
    private final AccountService accountService;
    private final KafkaEventProducer eventProducer;

    public void processEvent(Event event) {
        var acc = accountService.getAccountById(event.getAccountId());
        if (acc.isEmpty() || isValid(event, acc.get())) {
            handleFailedEvent(event.getId());
            logger.warn(ERROR_MESSAGE);
            return;
        }

        var account = acc.get();
        var cashAfter = account.getCashAmount() + event.getCash();
        account.setCashAmount(cashAfter);
        accountService.saveAccount(account);

        handleFinishedEvent(event.getId());
    }

    private boolean isValid(Event event, Account account) {
        return account.getCashAmount() + event.getCash() < 0;
    }

    private void handleFinishedEvent(Long eventId) {
        var processedEvent = ResponseEvent.builder()
                .id(eventId)
                .message(OK_MESSAGE)
                .build();
        eventProducer.sendFinishedEvent(processedEvent);
    }

    private void handleFailedEvent(Long eventId) {
        var failedEvent = ResponseEvent.builder()
                .id(eventId)
                .message(ERROR_MESSAGE)
                .build();
        eventProducer.sendFailEvent(failedEvent);
    }
}
