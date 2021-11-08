package ru.bank.application.queryhandler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import ru.bank.application.projection.entity.ApplicationEntity;
import ru.bank.application.projection.repository.ApplicationRepositoryHandler;
import ru.bank.application.queries.ApplicationQuery;
import ru.bank.application.queries.ListApplicationQuery;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationQueryHandler {

    private final ApplicationRepositoryHandler repositoryHandler;

    @QueryHandler
    public ApplicationEntity handle(ApplicationQuery query) {
        log.info("Handling ApplicationQuery: {}", query);

        return repositoryHandler.getApplicationEntity(query.getId());
    }

    @QueryHandler
    public List<ApplicationEntity> handle(ListApplicationQuery query) {
        log.info("Handling ListApplicationQuery: {}", query);

        return repositoryHandler.findAllByLastName(query.getLastName());
    }
}
