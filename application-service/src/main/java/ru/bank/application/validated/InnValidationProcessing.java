package ru.bank.application.validated;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.bank.application.dto.CreateApplicationRequest;

public class InnValidationProcessing implements
    ConstraintValidator<InnValidated, CreateApplicationRequest> {

    private static final Pattern INN_PATTERN = Pattern.compile("\\d{10}|\\d{12}");
    private static final int[] checkArr = new int[]{3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

    @Override
    public boolean isValid(CreateApplicationRequest request, ConstraintValidatorContext context) {
        return isValidInn(request.getInn());
    }

    private static boolean isValidInn(String inn) {
        inn = inn.trim();
        if (!INN_PATTERN.matcher(inn).matches()) {
            return false;
        }
        int length = inn.length();
        if (length == 12) {
            return innStep(inn, 2, 1) && innStep(inn, 1, 0);
        } else {
            return innStep(inn, 1, 2);
        }
    }

    private static boolean innStep(String inn, int offset, int arrOffset) {
        int sum = 0;
        int length = inn.length();
        for (int i = 0; i < length - offset; i++) {
            sum += (inn.charAt(i) - '0') * checkArr[i + arrOffset];
        }
        return (sum % 11) % 10 == inn.charAt(length - offset) - '0';
    }
}
