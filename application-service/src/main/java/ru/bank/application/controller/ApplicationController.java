package ru.bank.application.controller;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bank.application.dto.CreateApplicationRequest;
import ru.bank.application.projection.entity.ApplicationEntity;
import ru.bank.application.service.ApplicationCommandService;
import ru.bank.application.service.ApplicationQueryService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationCommandService applicationCommandService;
    private final ApplicationQueryService applicationQueryService;


    @PostMapping(value = "/create")
    public ResponseEntity<String> createApplication(
        @Valid @RequestBody CreateApplicationRequest request) {
        log.info("CreateApplicationRequest request: {}", request);

        var id = UUID.randomUUID().toString();
        request.setId(id);
        applicationCommandService.createApplication(request);
        return ResponseEntity.ok("Ваша заявка принята к рассмотрению. ID: " + id);
    }

    @GetMapping("/status/{applicationId}")
    public ApplicationEntity getApplication(@PathVariable String applicationId) {
        return applicationQueryService.getApplication(applicationId);
    }

    @GetMapping(value = "/statusForAll/{lastName}")
    public List<ApplicationEntity> getApplications(@PathVariable String lastName) {
        return applicationQueryService.getApplicationsByLastName(lastName);
    }
}
