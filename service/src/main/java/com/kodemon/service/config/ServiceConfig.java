package com.kodemon.service.config;

import com.kodemon.persistence.config.PersistenceConfig;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for the service module
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = "com.kodemon.service")
public class ServiceConfig {

    @Bean
    public Mapper dozerMapper() {
        return new DozerBeanMapper();
    }
}
