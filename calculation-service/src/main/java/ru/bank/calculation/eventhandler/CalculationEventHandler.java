package ru.bank.calculation.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import ru.bank.calculation.model.CalculationResult;
import ru.bank.calculation.service.CalculationService;
import ru.bank.calculation.service.ResolutionMakerService;
import ru.bank.common.events.CreateCalculationEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalculationEventHandler {

    private final CalculationService calculationService;
    private final ResolutionMakerService resolutionMakerService;

    @EventHandler
    public void createCalculationEventHandler(CreateCalculationEvent event) {
        log.info("CalculationEventHandler got CreateCalculationEvent: {}", event);
        final CalculationResult calculationResult = calculationService.calculateLoan(event);
        resolutionMakerService.makeResolution(calculationResult);
    }
}
