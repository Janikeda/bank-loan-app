package ru.bank.common.events;

import lombok.Data;

@Data
public class CreateCalculatedEvent {

    private final String applicationId;
    private final Long loanAmountApproved;
}
