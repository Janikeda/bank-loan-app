package ru.bank.validation.config;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app.web.client")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class WebClientProperties {

    private final long maxRetryAttempts;
    private final Duration retryDelay;
    private final int responseTimeout;
    private final int connectionTimeout;
}
