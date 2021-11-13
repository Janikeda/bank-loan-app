package ru.bank.validation.repository.blackList;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BlackListedPersonRepository extends ReactiveCrudRepository<BlackListedPerson, Long> {

    Mono<BlackListedPerson> findByInn(Long inn);
}
