package ru.bank.validation.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import ru.bank.common.events.CreateValidationEvent;
import ru.bank.validation.service.ValidationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidationEventHandler {

    private final ValidationService validationService;

    @EventHandler
    public void createValidationEventHandler(CreateValidationEvent event) {
        log.info("ValidationEventHandler got CreateValidationEvent: {}", event);
        validationService.validate(event)
            .subscribe(result -> log.info("Validation result: {}", result));
    }
}
