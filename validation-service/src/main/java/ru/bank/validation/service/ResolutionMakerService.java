package ru.bank.validation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import ru.bank.common.commands.CreateCalculationCommand;
import ru.bank.common.commands.CreateCancelCommand;
import ru.bank.common.events.Status;
import ru.bank.validation.dto.SalaryCheckResult;
import ru.bank.validation.model.ValidationResult;
import ru.bank.validation.repository.blackList.BlackListedPerson;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResolutionMakerService {

    private final CommandGateway commandGateway;

    Mono<ValidationResult> makeResolution(Tuple2<SalaryCheckResult, BlackListedPerson> tuple2) {
        var salaryCheckResult = tuple2.getT1();
        var blackListedPerson = tuple2.getT2();
        var result = new ValidationResult();
        if (blackListedPerson.getId() != null || salaryCheckResult.isCancelled()) {
            result.setIsPersonInBlackList(true);
            commandGateway.send(new CreateCancelCommand(salaryCheckResult.applicationId(),
                Status.CANCELED));
        } else if (salaryCheckResult.isApproved()) {
            result.setIsApproved(true);
            commandGateway.send(new CreateCalculationCommand(salaryCheckResult.applicationId(),
                Status.PENDING, salaryCheckResult.approvedAmount()));
        }

        return Mono.just(result);
    }
}
