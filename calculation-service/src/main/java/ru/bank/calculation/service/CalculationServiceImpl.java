package ru.bank.calculation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bank.calculation.model.CalculationResult;
import ru.bank.common.events.CreateCalculationEvent;

@Service
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    @Override
    public CalculationResult calculateLoan(CreateCalculationEvent event) {
        return new CalculationResult(event.getApplicationId(),
            (long) (event.getLoanAmountRequested() * (65.0f / 100.0f)));
    }
}
