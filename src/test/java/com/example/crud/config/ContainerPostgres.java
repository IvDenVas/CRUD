package com.example.crud.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class ContainerPostgres implements BeforeAllCallback {

    public PostgreSQLContainer<?> getContainer(){
        try(PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16.3")){
            container
                    .withDatabaseName("test")
                    .withUsername("user")
                    .withPassword("password")
                    .withExposedPorts(5432);
            return container;
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        PostgreSQLContainer<?> container = getContainer();
        container.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/test", container.getFirstMappedPort());
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "user");
        System.setProperty("spring.datasource.password", "password");
    }
}
