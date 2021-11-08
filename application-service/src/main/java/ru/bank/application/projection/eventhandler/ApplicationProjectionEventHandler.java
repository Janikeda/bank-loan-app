package ru.bank.application.projection.eventhandler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import ru.bank.application.projection.entity.ApplicationEntity;
import ru.bank.application.projection.repository.ApplicationRepositoryHandler;
import ru.bank.common.events.CreateApplicationEvent;
import ru.bank.common.events.CreateCalculatedEvent;
import ru.bank.common.events.CreateCancelEvent;
import ru.bank.common.events.Status;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationProjectionEventHandler {

    private final ApplicationRepositoryHandler repositoryHandler;

    @EventHandler
    public void applicationCreateApplicationEventHandler(CreateApplicationEvent event) {
        log.info("Applying CreateApplicationEvent: {}", event);

        var application = ApplicationEntity.builder()
            .id(event.getApplicationId())
            .name(event.getName())
            .lastName(event.getLastName())
            .age(event.getAge())
            .inn(Long.valueOf(event.getInn()))
            .email(event.getEmail())
            .loanAmountRequested(event.getLoanAmount())
            .phone(Long.valueOf(event.getPhone()))
            .createdDate(LocalDateTime.now())
            .build();

        repositoryHandler.save(application);
    }

    @EventHandler
    public void cancelApplicationEventHandler(CreateCancelEvent event) {
        final ApplicationEntity entity = repositoryHandler.getApplicationEntity(
            event.getApplicationId());
        entity.setStatus(Status.CANCELED);
    }

    @EventHandler
    public void createCalculatedEventHandler(CreateCalculatedEvent event) {
        final ApplicationEntity entity = repositoryHandler.getApplicationEntity(
            event.getApplicationId());
        entity.setStatus(Status.ACCEPTED);
        entity.setLoanAmountApproved(event.getLoanAmountApproved());
    }
}
