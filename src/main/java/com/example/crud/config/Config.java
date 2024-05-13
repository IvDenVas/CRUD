package com.example.crud.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс инициализации дополнительных бинов.
 */
@Configuration
public class Config {

    /**
     * Получение нового ModelMapper.
     *
     * @return new ModelMapper
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
