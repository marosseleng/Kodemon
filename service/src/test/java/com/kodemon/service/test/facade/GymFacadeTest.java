package com.kodemon.service.test.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.dto.UserDTO;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.persistence.dao.GymDao;
import com.kodemon.persistence.dao.TrainerDao;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.GymService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * @author Oliver Roch
 */
@ContextConfiguration(classes=ServiceConfig.class)
public class GymFacadeTest extends AbstractTestNGSpringContextTests {
    @Inject
    private GymService gymService;

    @Inject
    private GymFacade gymFacade;

    @BeforeMethod
    public void prepare() {
        gymService.initializeGyms();
    }


    @Test
    public void findAllTest() {
        List<GymDTO> gyms = gymFacade.findAll();
        assertThat(gyms.size(), is(8));
    }
}
