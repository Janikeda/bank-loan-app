package ru.bank.application.service;

import ru.bank.application.dto.CreateApplicationRequest;

public interface ApplicationCommandService {

    void createApplication(CreateApplicationRequest request);
}
