package com.example.crud.config;

import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс инициализации дополнительных бинов.
 */
@Configuration
public class Config {
    /**
     * Настройка ModelMapper.
     *
     * @return mapper.
     */
    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * Кастомная настройка Spring Boot.
     * решение ошибки: The valid characters are defined in RFC 7230 and RFC 3986
     *
     * @return factory
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
        return factory;
    }
}
