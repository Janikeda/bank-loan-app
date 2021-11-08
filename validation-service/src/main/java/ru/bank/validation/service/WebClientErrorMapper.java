package ru.bank.validation.service;

import java.util.function.Function;

public interface WebClientErrorMapper extends Function<Throwable, Throwable> {

    @Override
    default Throwable apply(Throwable throwable) {
        return mapError(throwable);
    }

    Throwable mapError(Throwable throwable);

}
