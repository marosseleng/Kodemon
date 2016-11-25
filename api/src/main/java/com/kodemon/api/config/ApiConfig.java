package com.kodemon.api.config;

import com.kodemon.api.facade.FightFacade;
import com.kodemon.persistence.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration for the api module
 *
 * @author <a href="mailto:xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackageClasses = FightFacade.class)
public class ApiConfig {
}
