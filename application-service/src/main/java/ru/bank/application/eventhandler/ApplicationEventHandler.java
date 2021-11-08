package ru.bank.application.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import ru.bank.application.mail.EmailService;
import ru.bank.application.projection.entity.ApplicationEntity;
import ru.bank.application.projection.repository.ApplicationRepositoryHandler;
import ru.bank.common.events.CreateCalculatedEvent;
import ru.bank.common.events.CreateCancelEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationEventHandler {

    private static final String MAIL_SUBJECT = "Сообщение от Банка";

    private final EmailService emailService;
    private final ApplicationRepositoryHandler repositoryHandler;

    @EventHandler
    public void cancelApplicationEventHandler(CreateCancelEvent event) {
        log.info("ApplicationEventHandler got CreateCancelEvent: {}", event);

        final ApplicationEntity entity = repositoryHandler.getApplicationEntity(
            event.getApplicationId());
        String message = String.format("Добрый день, %s %s!\nВаш кредит на сумму: %s отклонен",
            entity.getName(), entity.getLastName(), entity.getLoanAmountRequested());
        emailService.sendSimpleMessage(entity.getEmail(), MAIL_SUBJECT, message);
    }

    @EventHandler
    public void createCalculatedEventHandler(CreateCalculatedEvent event) {
        log.info("ApplicationEventHandler got CreateCalculatedEvent: {}", event);

        final ApplicationEntity entity = repositoryHandler.getApplicationEntity(
            event.getApplicationId());
        String message = String.format(
            "Добрый день, %s %s!\nВаш кредит одобрен. Сумма согласованного кредита: %s",
            entity.getName(), entity.getLastName(), event.getCalculatedLoan());
        emailService.sendSimpleMessage(entity.getEmail(), MAIL_SUBJECT, message);
    }
}
