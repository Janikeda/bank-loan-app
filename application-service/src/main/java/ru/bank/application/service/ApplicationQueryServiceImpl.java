package ru.bank.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;
import ru.bank.application.queries.ApplicationQuery;
import ru.bank.application.queries.ListApplicationQuery;
import ru.bank.application.projection.entity.ApplicationEntity;

@RequiredArgsConstructor
@Service
public class ApplicationQueryServiceImpl implements ApplicationQueryService {

    private final QueryGateway queryGateway;

    @Override
    public ApplicationEntity getApplication(String id) {
        return queryGateway.query(new ApplicationQuery(id), ApplicationEntity.class).join();
    }

    @Override
    public List<ApplicationEntity> getApplicationsByLastName(String lastName) {
        return queryGateway
            .query(new ListApplicationQuery(lastName),
                ResponseTypes.multipleInstancesOf(ApplicationEntity.class))
            .join();
    }
}
