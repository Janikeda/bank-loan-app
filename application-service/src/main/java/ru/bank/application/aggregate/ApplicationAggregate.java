package ru.bank.application.aggregate;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import ru.bank.common.commands.CreateApplicationCommand;
import ru.bank.common.commands.CreateCalculatedCommand;
import ru.bank.common.commands.CreateCalculationCommand;
import ru.bank.common.commands.CreateCancelCommand;
import ru.bank.common.events.CreateApplicationEvent;
import ru.bank.common.events.CreateCalculatedEvent;
import ru.bank.common.events.CreateCalculationEvent;
import ru.bank.common.events.CreateCancelEvent;
import ru.bank.common.events.CreateValidationEvent;
import ru.bank.common.events.Status;

@Aggregate
@NoArgsConstructor
@Slf4j
public class ApplicationAggregate {

    @AggregateIdentifier
    private String applicationId;
    private String name;
    private String lastName;
    private Integer age;
    private String inn;
    private String email;
    private String phone;
    private Long loanAmountRequested;
    private Long loanAmountApproved;

    private String status;


    @CommandHandler
    public ApplicationAggregate(CreateApplicationCommand command) {
        log.info("Handling CreateApplicationCommand: {}", command);
        var applicationEvent = CreateApplicationEvent.builder()
            .applicationId(command.getApplicationId())
            .name(command.getName())
            .lastName(command.getLastName())
            .age(command.getAge())
            .inn(command.getInn())
            .email(command.getEmail())
            .phone(command.getPhone())
            .loanAmount(command.getLoanAmount())
            .build();
        AggregateLifecycle.apply(applicationEvent);
    }

    @EventSourcingHandler
    protected void on(CreateApplicationEvent event) {
        this.applicationId = event.getApplicationId();
        this.name = event.getName();
        this.lastName = event.getLastName();
        this.age = event.getAge();
        this.inn = event.getInn();
        this.email = event.getEmail();
        this.phone = event.getPhone();
        this.loanAmountRequested = event.getLoanAmount();
        this.status = String.valueOf(Status.CREATED);

        AggregateLifecycle.apply(
            new CreateValidationEvent(this.applicationId, this.name, this.lastName, this.inn,
                this.loanAmountRequested));
    }

    @CommandHandler
    protected void on(CreateCancelCommand command) {
        log.info("Handling CreateCancelCommand: {}", command);
        this.status = String.valueOf(command.getStatus());
        AggregateLifecycle.apply(
            new CreateCancelEvent(this.applicationId));
    }

    @CommandHandler
    public void on(CreateCalculationCommand command) {
        log.info("Handling CreateCalculationCommand: {}", command);
        this.status = String.valueOf(command.getStatus());
        AggregateLifecycle.apply(
            new CreateCalculationEvent(this.applicationId, this.loanAmountRequested));
    }

    @CommandHandler
    public void on(CreateCalculatedCommand command) {
        log.info("Handling CreateCalculatedCommand: {}", command);
        this.status = String.valueOf(command.getStatus());
        this.loanAmountApproved = command.getCalculatedLoan();
        AggregateLifecycle.apply(
            new CreateCalculatedEvent(this.applicationId, this.loanAmountApproved));
    }

}
