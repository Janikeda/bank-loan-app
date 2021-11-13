package ru.bank.validation.config;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import java.util.StringJoiner;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(WebClientProperties.class)
public class WebClientConfig {

    private final WebClient.Builder webClient;
    private final WebClientProperties properties;

    @Bean
    public WebClient webClient() {
        var httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.getConnectionTimeout())
            .responseTimeout(Duration.ofMillis(properties.getResponseTimeout()));

        return webClient
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logRequest());
                exchangeFilterFunctions.add(logResponse());
            })
            .build();
    }

    ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("{} Response: status: {}", clientResponse.logPrefix(),
                clientResponse.statusCode());
            var stringJoiner = new StringJoiner(", ", "Header: ", "");
            clientResponse
                .headers()
                .asHttpHeaders()
                .forEach((name, values) -> values.forEach(
                    value -> stringJoiner.add(name + "=" + value)));
            log.info(stringJoiner.toString());
            return Mono.just(clientResponse);
        });
    }

    ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("{} Request: method={}, url={}", clientRequest.logPrefix(),
                clientRequest.method(), clientRequest.url());
            var stringJoiner = new StringJoiner(", ", "Header: ", "");
            clientRequest
                .headers()
                .forEach((name, values) -> values.forEach(
                    value -> stringJoiner.add(name + "=" + value)));
            log.info(stringJoiner.toString());
            return Mono.just(clientRequest);
        });
    }

    @Bean
    public Retry salaryRetrySpec() {
        return retrySpec(properties.getMaxRetryAttempts(), properties.getRetryDelay(),
            httpStatus -> httpStatus.is5xxServerError() || httpStatus == HttpStatus.NOT_FOUND);
    }

    private static Retry retrySpec(long maxRetryAttempts, Duration retryDelay,
        Predicate<HttpStatus> httpStatusPredicate) {

        return Retry.fixedDelay(maxRetryAttempts, retryDelay)
            .filter(throwable -> !(throwable instanceof WebClientResponseException) ||
                httpStatusPredicate.test(((WebClientResponseException)throwable).getStatusCode()))
            .onRetryExhaustedThrow((spec, signal) -> signal.failure());
    }
}
