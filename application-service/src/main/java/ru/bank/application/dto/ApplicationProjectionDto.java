package ru.bank.application.dto;

import java.time.LocalDateTime;
import lombok.Data;
import ru.bank.common.events.Status;

@Data
public class ApplicationProjectionDto {

    private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";


    private String name;
    private String lastName;
    private Integer age;
    private Long inn;
    private String email;
    private Long phone;
    private Long loanAmountRequested;
    private LocalDateTime createdDate;
    private Status status;
    private Long loanAmountApproved;
}
