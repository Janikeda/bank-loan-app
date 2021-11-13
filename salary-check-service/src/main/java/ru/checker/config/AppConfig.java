package ru.checker.config;

import static ru.checker.mock.ResponseType.*;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.checker.dto.ResponseDto;
import ru.checker.mock.ResponseType;
import ru.checker.mock.SalaryCheckCreator;

@Configuration
public class AppConfig {

    @Bean
    public Map<ResponseType, SalaryCheckCreator> salaryCheckCreatorMap() {
        return Map.of(
            TOTALLY_APPROVED,
            (applicationId, loanAmount) -> new ResponseDto(applicationId, true, false, loanAmount),
            PARTLY_APPROVED,
            (applicationId, loanAmount) -> new ResponseDto(applicationId, true, false,
                (long) (loanAmount * (75.0f / 100.0f))),
            CANCELLED,
            (applicationId, loanAmount) -> new ResponseDto(applicationId, false, true, null)
        );
    }
}
