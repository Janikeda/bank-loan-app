package ru.bank.application.validated;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.bank.application.dto.CreateApplicationRequest;

public class EmailValidationProcessing implements
    ConstraintValidator<EmailValidated, CreateApplicationRequest> {

    private static final Pattern EMAIL_PATTERN = Pattern
        .compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    @Override
    public boolean isValid(CreateApplicationRequest request, ConstraintValidatorContext context) {
        return isValidEmail(request.getEmail());
    }

    private static boolean isValidEmail(String emailAddress) {
        return EMAIL_PATTERN
            .matcher(emailAddress)
            .matches();
    }

}
