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
    private TrainerDao trainerDao;

    @Inject
    private GymDao gymDao;

    @Inject
    private GymFacade gymFacade;

    private Gym gym;
    private GymDTO gymDTO;
    private Trainer trainer;
    private UserDTO userDTO;

    @BeforeMethod
    public void prepare() {
        trainer = new Trainer();
        trainer.setUserName("TR123");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        trainer.setFirstName("Harry");
        trainer.setLastName("Potter");

        userDTO = new UserDTO();
        userDTO.setUserName("TR123");
        Date dob2 = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        userDTO.setDateOfBirth(dob);
        userDTO.setFirstName("Harry");
        userDTO.setLastName("Potter");

        trainerDao.save(trainer);

        gym = new Gym();
        gym.setTrainer(trainer);
        gym.setType(PokemonType.BUG);
        gym.setCity("New York");

        gymDTO = new GymDTO();
        gymDTO.setTrainer(userDTO);
        gymDTO.setType(PokemonType.BUG);
        gymDTO.setCity("New York");

        gymDao.save(gym);
    }


    @Test
    public void findAllTest() {
        List<GymDTO> gyms = gymFacade.findAll();
        assertThat(gyms.size(), is(1));
        assertThat(gyms.get(0), is(gymDTO));
    }
}
