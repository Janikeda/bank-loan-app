package ru.bank.common.events;

import lombok.Data;

@Data
public class CreateValidationEvent {

    private final String applicationId;
    private final String name;
    private final String lastName;
    private final String inn;
    private final Long loanAmount;
}
