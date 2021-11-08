package ru.bank.validation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.bank.common.events.CreateValidationEvent;
import ru.bank.validation.dto.SalaryCheck;
import ru.bank.validation.dto.SalaryCheckResult;
import ru.bank.validation.exception.model.GatewayException;
import ru.bank.validation.exception.model.SalaryCheckDuplicatedException;
import ru.bank.validation.model.ValidationResult;
import ru.bank.validation.repository.blackList.BlackListedPerson;
import ru.bank.validation.repository.blackList.BlackListedPersonRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    private final BlackListedPersonRepository blackListedPersonRepository;
    private final WebClient webClient;
    private final Retry salaryRetrySpec;
    private final WebClientErrorMapper errorMapper;
    private final ResolutionMakerService resolutionMaker;
    @Value("${app.web.client.salary.check.url}")
    private String salaryCheckUrl;

    @Override
    public ValidationResult validate(CreateValidationEvent event) {
        Mono<SalaryCheckResult> salaryCheckResult = salaryCheck(
            new SalaryCheck(event.getApplicationId(), event.getInn(), event.getLoanAmount()));

        Mono<BlackListedPerson> blackListedPerson = blackListedPersonRepository
            .findByInn(Long.valueOf(event.getInn()))
            .switchIfEmpty(Mono.just(new BlackListedPerson()));

        return Mono.zip(salaryCheckResult, blackListedPerson)
            .flatMap(resolutionMaker::makeResolution)
            .block();
    }

    private Mono<SalaryCheckResult> salaryCheck(SalaryCheck salaryCheck) {
        try {
            return webClient.post()
                .uri(salaryCheckUrl)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(salaryCheck)
                .retrieve()
                .bodyToMono(SalaryCheckResult.class)
                .retryWhen(salaryRetrySpec)
                .onErrorMap(errorMapper);
        } catch (GatewayException exception) {
            var httpStatus = exception.getHttpStatus();
            if (httpStatus.isPresent() && httpStatus.get() == HttpStatus.CONFLICT) {
                throw new SalaryCheckDuplicatedException("Salary check duplicated", exception);
            }
            throw exception;
        }
    }
}
