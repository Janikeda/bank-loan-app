package ru.bank.calculation.service;

import ru.bank.calculation.model.CalculationResult;
import ru.bank.common.events.CreateCalculationEvent;

public interface CalculationService {

    CalculationResult calculateLoan(CreateCalculationEvent event);
}
