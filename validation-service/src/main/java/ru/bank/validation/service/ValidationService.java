package ru.bank.validation.service;

import ru.bank.common.events.CreateValidationEvent;
import ru.bank.validation.model.ValidationResult;

public interface ValidationService {

    ValidationResult validate(CreateValidationEvent event);
}
