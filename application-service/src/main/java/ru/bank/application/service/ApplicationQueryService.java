package ru.bank.application.service;

import java.util.List;
import ru.bank.application.projection.entity.ApplicationEntity;

public interface ApplicationQueryService {

    ApplicationEntity getApplication(String id);

    List<ApplicationEntity> getApplicationsByLastName(String lastName);
}
