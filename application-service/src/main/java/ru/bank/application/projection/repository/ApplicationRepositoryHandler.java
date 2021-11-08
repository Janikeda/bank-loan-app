package ru.bank.application.projection.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bank.application.projection.entity.ApplicationEntity;

@Service
@RequiredArgsConstructor
public class ApplicationRepositoryHandler {

    private final ApplicationRepository applicationRepository;

    public void save(ApplicationEntity entity) {
        applicationRepository.save(entity);
    }

    public ApplicationEntity getApplicationEntity(String id) {
        return applicationRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ApplicationEntity not found"));
    }

    public List<ApplicationEntity> findAllByLastName(String lastName) {
        return applicationRepository.findAllByLastName(lastName);
    }

    @Transactional
    public int removeOldRecords(LocalDateTime date) {
        return applicationRepository.removeOldRecords(date);
    }
}
