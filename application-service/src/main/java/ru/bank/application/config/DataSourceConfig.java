package ru.bank.application.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    private static final long VALIDATION_TIMEOUT = 500;
    private static final int MAX_LIFETIME_MS = 60_000;

    @Bean(destroyMethod = "close")
    public HikariDataSource getDataSource(@Value("${app.datasource.url}") String dbUrl,
        @Value("${app.datasource.username}") String dbUser,
        @Value("${app.db.password}") String dbPassword,
        @Value("${app.db.min.pool.size}") int minPoolSize,
        @Value("${app.db.max.pool.size}") int maxPoolSize,
        @Value("${app.db.connection.timeout}") long connectionTimeout) {

        var hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dbUrl);
        hikariDataSource.setUsername(dbUser);
        hikariDataSource.setPassword(dbPassword);
        hikariDataSource.setMaximumPoolSize(maxPoolSize);
        hikariDataSource.setMaxLifetime(MAX_LIFETIME_MS);
        hikariDataSource.setMinimumIdle(minPoolSize);
        hikariDataSource.setConnectionTimeout(connectionTimeout);
        hikariDataSource.setValidationTimeout(VALIDATION_TIMEOUT);
        hikariDataSource.setAutoCommit(false);

        return hikariDataSource;
    }
}
