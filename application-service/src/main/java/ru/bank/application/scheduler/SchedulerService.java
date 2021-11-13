package ru.bank.application.scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bank.application.projection.repository.ApplicationRepositoryHandler;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        "dd.MM.yyyy HH:mm:ss");

    private final ApplicationRepositoryHandler repositoryHandler;

    @Scheduled(cron = "${cron.delete.records}")
    public void deleteOldRecords() {
        var dateTime = LocalDateTime.now();
        final int result = repositoryHandler.removeOldRecords(dateTime.minusHours(24L));
        log.info("SchedulerService. Date: {}. Number of deleted records: {}",
            dateTime.format(formatter), result);
    }
}
