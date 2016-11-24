package com.kodemon.service.config;

import com.kodemon.persistence.config.PersistenceConfig;
import com.kodemon.service.implementations.BadgeServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring configuration for the service module
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = BadgeServiceImpl.class)
public class ServiceConfig {

    @Bean
    public Mapper dozerMapper() {
        List<String> mappings = new ArrayList<>();
        mappings.add("dozer-config.xml");
        return new DozerBeanMapper(mappings);
    }

    /**
     * Custom config for Dozer if needed
     * @author nguyen
     *
     */
    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
//            mapping(Pok.class, CategoryDTO.class);
        }
    }
}
