package ru.checker.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.checker.dto.RequestDto;
import ru.checker.dto.ResponseDto;
import ru.checker.mock.ResponseType;
import ru.checker.mock.SalaryCheckCreator;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SalaryCheckController {

    private final Map<ResponseType, SalaryCheckCreator> salaryCheckCreatorMap;

    @PostMapping(path = "/check")
    public ResponseEntity<ResponseDto> checkSalary(@RequestBody RequestDto requestDto) {
        log.info("SalaryCheckRequest: {}", requestDto);
        return ResponseEntity.ok(getRandomResponseDto(requestDto));
    }

    private ResponseDto getRandomResponseDto(RequestDto requestDto) {
        List<ResponseType> keysAsArray = new ArrayList<>(salaryCheckCreatorMap.keySet());
        var random = new Random();

        final ResponseType key = keysAsArray.get(random.nextInt(keysAsArray.size()));
        final SalaryCheckCreator salaryCheckCreator = salaryCheckCreatorMap.get(key);
        return salaryCheckCreator.createResponseDto(requestDto.applicationId(), requestDto.loanAmount());
    }
}
