package ru.bank.application.service;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import ru.bank.common.commands.CreateApplicationCommand;
import ru.bank.application.dto.CreateApplicationRequest;

@Service
@RequiredArgsConstructor
public class ApplicationCommandServiceImpl implements ApplicationCommandService {

    private final CommandGateway commandGateway;

    @Override
    public void createApplication(CreateApplicationRequest request) {
        var command = CreateApplicationCommand.builder()
            .applicationId(request.getId())
            .name(request.getName())
            .lastName(request.getLastName())
            .age(request.getAge())
            .inn(request.getInn())
            .email(request.getEmail())
            .phone(request.getPhone())
            .loanAmount(request.getLoanAmount())
            .build();
        commandGateway.send(command);
    }
}
