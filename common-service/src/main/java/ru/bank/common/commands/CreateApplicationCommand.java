package ru.bank.common.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class CreateApplicationCommand {

    @TargetAggregateIdentifier
    private final String applicationId;
    private final String name;
    private final String lastName;
    private final Integer age;
    private final String inn;
    private final String email;
    private final String phone;
    private final Long loanAmount;
}
