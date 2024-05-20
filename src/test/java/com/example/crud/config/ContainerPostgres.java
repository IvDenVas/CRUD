package com.example.crud.config;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerPostgres implements BeforeAllCallback, AfterAllCallback {
    private PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("password")
            .withExposedPorts(5432);

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/test", postgres.getFirstMappedPort());
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "user");
        System.setProperty("spring.datasource.password", "password");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        postgres.stop();
    }
}
