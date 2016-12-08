package com.kodemon.service.config;

import com.kodemon.persistence.config.PersistenceConfig;
import com.kodemon.service.facade.FightFacadeImpl;
import com.kodemon.service.implementations.BadgeServiceImpl;
import com.kodemon.service.util.OrikaMapper;
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
@ComponentScan(basePackageClasses = {BadgeServiceImpl.class, FightFacadeImpl.class, OrikaMapper.class})
public class ServiceConfig {
}
