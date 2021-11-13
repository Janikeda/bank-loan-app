package ru.bank.calculation.model;

import lombok.Data;

@Data
public class CalculationResult {

    private final String applicationId;
    private final Long finalAmount;
}
