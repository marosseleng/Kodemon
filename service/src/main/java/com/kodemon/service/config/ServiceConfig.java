package com.kodemon.service.config;

import com.kodemon.api.dto.BadgeDTO;
import com.kodemon.api.dto.PokemonDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.persistence.config.PersistenceConfig;
import com.kodemon.persistence.entity.Badge;
import com.kodemon.persistence.entity.Pokemon;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.service.implementations.BadgeServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.classmap.RelationshipType;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
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
@ComponentScan(basePackageClasses = BadgeServiceImpl.class)
public class ServiceConfig {

    @Bean
    public Mapper dozerMapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(customMapping());

        return mapper;
    }

    private BeanMappingBuilder customMapping() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Pokemon.class, PokemonDTO.class);
                mapping(Badge.class, BadgeDTO.class);
                mapping(Trainer.class, UserDTO.class)
                        .fields("pokemons", "pokemons", FieldsMappingOptions.relationshipType(RelationshipType.NON_CUMULATIVE))
                        .fields("badges", "badges", FieldsMappingOptions.relationshipType(RelationshipType.NON_CUMULATIVE));
            }
        };
    }
}
