package ru.bank.validation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.timeout.ReadTimeoutException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.bank.validation.exception.model.GatewayException;
import ru.bank.validation.exception.model.GatewayTimeoutException;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebClientErrorMapperImpl implements WebClientErrorMapper {

    private static final TypeReference<Map<String, Object>> ERROR_RESPONSE_BODY_TYPE_REF = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;

    @Override
    public Throwable mapError(Throwable throwable) {
        if (throwable instanceof WebClientResponseException) {
            return mapWebClientResponseException((WebClientResponseException)throwable);
        } else if (throwable instanceof WebClientException) {
            return mapWebClientException((WebClientException)throwable);
        } else if (throwable instanceof Exception) {
            return mapException((Exception)throwable);
        }

        return throwable;
    }

    private Throwable mapWebClientResponseException(WebClientResponseException exception) {
        var message = Optional.ofNullable(exception.getHeaders().get("pstxid"))
            .map(pstxid -> exception.getResponseBodyAsString() + "\npstxid: " + pstxid)
            .orElseGet(exception::getResponseBodyAsString);

        return new GatewayException(exception.getStatusCode(),
            tryToParseResponseBody(exception.getResponseBodyAsByteArray()), message, exception);
    }

    private Map<String, Object> tryToParseResponseBody(byte[] responseBody) {
        try {
            return objectMapper.readValue(responseBody, ERROR_RESPONSE_BODY_TYPE_REF);
        } catch (Exception exception) {
            return Map.of();
        }
    }

    private static Throwable mapWebClientException(WebClientException exception) {
        var cause = exception.getMostSpecificCause();

        if (cause instanceof ReadTimeoutException) {
            return new GatewayTimeoutException("Couldn't execute request", exception);
        }

        return mapException(exception);
    }

    private static Throwable mapException(Exception exception) {
        return new GatewayException("Couldn't execute request", exception);
    }
}
