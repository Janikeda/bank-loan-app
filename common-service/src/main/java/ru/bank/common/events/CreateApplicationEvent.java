package ru.bank.common.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateApplicationEvent {

    private final String applicationId;
    private final String name;
    private final String lastName;
    private final Integer age;
    private final String inn;
    private final String email;
    private final String phone;
    private final Long loanAmount;
}
