package com.kodemon.service.util;

import com.kodemon.api.dto.*;
import com.kodemon.persistence.entity.*;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Custom-configured Orika mapper bean
 *
 * @author <a href="mailto:xseleng@gi.muni.cz">Maros Seleng, 422624</a>
 */
@Component
public class OrikaMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Trainer.class, UserDTO.class).byDefault().register();
        factory.classMap(Pokemon.class, PokemonDTO.class).byDefault().register();
        factory.classMap(Badge.class, BadgeDTO.class).byDefault().register();
        factory.classMap(Gym.class, GymDTO.class).byDefault().register();
        factory.classMap(TrainerFight.class, FightDTO.class).byDefault().register();
    }
}
