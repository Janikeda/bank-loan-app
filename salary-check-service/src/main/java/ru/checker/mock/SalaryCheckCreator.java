package ru.checker.mock;

import ru.checker.dto.ResponseDto;

@FunctionalInterface
public interface SalaryCheckCreator {

    ResponseDto createResponseDto(String applicationId, Long loanAmount);
}
