package ru.bank.common.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import ru.bank.common.events.Status;

@Data
public class CreateCancelCommand {

    @TargetAggregateIdentifier
    private final String applicationId;
    private final Status status;
}
