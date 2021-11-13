package ru.bank.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import ru.bank.application.validated.EmailValidated;
import ru.bank.application.validated.InnValidated;

@Data
@InnValidated
@EmailValidated
public class CreateApplicationRequest {

    @NotBlank(message = "Имя обязательно") String name;
    @NotBlank(message = "Фамилия обязательна") String lastName;
    @NotNull(message = "Возраст обязателен") Integer age;
    @NotBlank(message = "ИНН обязателен") String inn;
    @NotBlank(message = "Почта обязательна") String email;
    @Pattern(regexp = "^9\\d{9}$", message = "Телефон должен "
        + "состоять из 10 цифр и начинаться с цифры 9") String phone;
    @NotNull(message = "Сумма кредита обязательна") Long loanAmount;

    String id;
}
