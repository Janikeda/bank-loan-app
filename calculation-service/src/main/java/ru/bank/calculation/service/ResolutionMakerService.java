package ru.bank.calculation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import ru.bank.calculation.model.CalculationResult;
import ru.bank.common.commands.CreateCalculatedCommand;
import ru.bank.common.events.Status;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResolutionMakerService {

    private final CommandGateway commandGateway;

    public void makeResolution(CalculationResult calculationResult) {
        commandGateway.send(
            new CreateCalculatedCommand(calculationResult.getApplicationId(),
                Status.ACCEPTED,
                calculationResult.getFinalAmount()));
    }
}
