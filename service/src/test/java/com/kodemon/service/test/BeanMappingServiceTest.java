package com.kodemon.service.test;

import com.kodemon.api.dto.FightDTO;
import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.entity.TrainerFight;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.BeanMappingService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by mseleng on 11/24/16.
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests {

    @Inject
    private BeanMappingService service;

    Trainer trainer;
    UserDTO userDTO;

    Gym gym;
    GymDTO gymDTO;

    TrainerFight trainerFight;
    FightDTO fightDTO;

    @Test
    void testMapTrainerToUserDTO() {

    }

    @Test
    void testMapUserDTOToTrainer() {

    }

    @Test
    void testMapTrainerFightToFightDTO() {

    }

    @Test
    void testMapFightDTOToTrainerFight() {

    }

    @Test
    void testMapGymDtoToGym() {

    }

    @Test
    void testMapGymToGymDTO() {

    }
}
