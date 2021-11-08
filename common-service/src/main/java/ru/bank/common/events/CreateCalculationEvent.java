package ru.bank.common.events;

import lombok.Data;

@Data
public class CreateCalculationEvent {

    private final String applicationId;
    private final Long loanAmount;
}
