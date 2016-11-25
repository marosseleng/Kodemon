package com.kodemon.service.test.facade;

import com.kodemon.api.dto.GymDTO;
import com.kodemon.api.facade.GymFacade;
import com.kodemon.persistence.entity.Gym;
import com.kodemon.persistence.entity.Trainer;
import com.kodemon.persistence.enums.PokemonType;
import com.kodemon.service.config.ServiceConfig;
import com.kodemon.service.interfaces.BeanMappingService;
import com.kodemon.service.interfaces.GymService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Oliver Roch
 */
@ContextConfiguration(classes=ServiceConfig.class)
public class GymFacadeTest extends AbstractTestNGSpringContextTests {
    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private GymService gymService;

    @Inject
    @InjectMocks
    private GymFacade gymFacade;

    private Gym gym;
    private GymDTO gymDTO;
    private Trainer trainer;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllTest() {
        trainer = new Trainer();
        trainer.setUserName("TR123");
        Date dob = new Calendar.Builder().setDate(1987, 4, 1).build().getTime();
        trainer.setDateOfBirth(dob);
        trainer.setFirstName("Harry");
        trainer.setLastName("Potter");

        gym = new Gym();
        gym.setTrainer(trainer);
        gym.setType(PokemonType.BUG);
        gym.setCity("New York");

        when(gymService.findAll()).thenReturn(Collections.singletonList(gym));
        when(beanMappingService.mapTo(Collections.singletonList(gym), GymDTO.class)).thenReturn(Collections.singletonList(gymDTO));

        List<GymDTO> gyms = gymFacade.findAll();
        assertThat(gyms.size(), is(1));

        verify(gymService).findAll();
        verify(beanMappingService).mapTo(Collections.singletonList(gym), GymDTO.class);
    }
}
