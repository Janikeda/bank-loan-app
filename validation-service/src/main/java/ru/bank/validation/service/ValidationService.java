package ru.bank.validation.service;

import reactor.core.publisher.Mono;
import ru.bank.common.events.CreateValidationEvent;
import ru.bank.validation.model.ValidationResult;

public interface ValidationService {

    Mono<ValidationResult> validate(CreateValidationEvent event);
}
